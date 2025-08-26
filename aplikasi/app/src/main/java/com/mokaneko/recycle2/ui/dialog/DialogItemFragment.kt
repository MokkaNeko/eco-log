package com.mokaneko.recycle2.ui.dialog

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mokaneko.recycle2.databinding.FragmentDialogItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogItemFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDialogItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DialogItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUriString = arguments?.getString("image_uri")
        val imageUri = imageUriString?.let { Uri.parse(it) }
        val kategoriArg = arguments?.getString("kategori")
        val subkategoriArg = arguments?.getString("subkategori")

        // Observe kategori
        viewModel.kategoriList.observe(viewLifecycleOwner) { list ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, list)
            binding.sheetDropdownKategori.setAdapter(adapter)

            kategoriArg?.let { kategori ->
                if (list.contains(kategori)) {
                    binding.sheetDropdownKategori.setText(kategori, false)
                    viewModel.loadSubkategoriByKategori(kategori)
                }
            }
        }

        // Observe subkategori
        viewModel.subkategoriList.observe(viewLifecycleOwner) { list ->
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, list)
            binding.sheetDropdownSubkategori.setAdapter(adapter)
            binding.sheetDropdownSubkategori.setText("") // Reset

            subkategoriArg?.let { subkategori ->
                if (list.contains(subkategori)) {
                    binding.sheetDropdownSubkategori.setText(subkategori, false)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.sheetButtonSave.isEnabled = !isLoading
            binding.sheetNamaItem.isEnabled = !isLoading
            binding.sheetDropdownKategori.isEnabled = !isLoading
            binding.sheetDropdownSubkategori.isEnabled = !isLoading
            binding.sheetDescription.isEnabled = !isLoading
        }

        // Load kategori saat awal tampil
        viewModel.loadKategori()

        binding.sheetDropdownKategori.setOnItemClickListener { _, _, position, _ ->
            val kategori = viewModel.kategoriList.value?.get(position) ?: return@setOnItemClickListener
            viewModel.loadSubkategoriByKategori(kategori)
        }

        binding.sheetDescription.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.post {
                    view.requestFocus()
                    view.scrollTo(0, view.bottom)
                    val imm =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        }


                binding.sheetButtonSave.setOnClickListener {
            val nama = binding.sheetNamaItem.text.toString()
            val kategori = binding.sheetDropdownKategori.text.toString()
            val subkategori = binding.sheetDropdownSubkategori.text.toString()
            val deskripsi = binding.sheetDescription.text.toString()

            if (!viewModel.validateInput(nama, kategori, subkategori)) {
                Toast.makeText(requireContext(), "Lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imageUri == null) {
                Toast.makeText(requireContext(), "Gambar tidak ditemukan!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.uploadTrashItem(
                nama = nama,
                kategori = kategori,
                subkategori = subkategori,
                deskripsi = deskripsi,
                imageUri = imageUri,
                context = requireContext(),
                onSuccess = {
                    Toast.makeText(requireContext(), "Item berhasil disimpan", Toast.LENGTH_SHORT).show()
                    dismiss()
                },
                onFailure = { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}