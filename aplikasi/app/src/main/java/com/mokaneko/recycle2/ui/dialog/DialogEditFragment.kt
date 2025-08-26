package com.mokaneko.recycle2.ui.dialog

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.databinding.FragmentDialogEditBinding
import com.mokaneko.recycle2.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogEditFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDialogEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DialogEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageUriString = arguments?.getString("image_uri")
        val imageUri = imageUriString?.let { Uri.parse(it) }
        val titleArg = arguments?.getString("title")
        val kategoriArg = arguments?.getString("kategori")
        val subkategoriArg = arguments?.getString("subkategori")
        val descriptionArg = arguments?.getString("description")
        val itemId = arguments?.getString("id")
        val isTerbuang = arguments?.getBoolean("isTerbuang") ?: false

        binding.sheetNamaItem.setText(titleArg ?: "")
        binding.sheetDescription.setText(descriptionArg ?: "")

        viewModel.loadKategori()

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
            binding.sheetButtonEdit.isEnabled = !isLoading
            binding.sheetNamaItem.isEnabled = !isLoading
            binding.sheetDropdownKategori.isEnabled = !isLoading
            binding.sheetDropdownSubkategori.isEnabled = !isLoading
            binding.sheetDescription.isEnabled = !isLoading
        }

        binding.sheetDropdownKategori.setOnItemClickListener { _, _, position, _ ->
            val kategori = viewModel.kategoriList.value?.get(position) ?: return@setOnItemClickListener
            viewModel.loadSubkategoriByKategori(kategori)
        }

        binding.sheetButtonEdit.setOnClickListener {
            val nama = binding.sheetNamaItem.text.toString()
            val kategori = binding.sheetDropdownKategori.text.toString()
            val subkategori = binding.sheetDropdownSubkategori.text.toString()
            val deskripsi = binding.sheetDescription.text.toString()

            if (!viewModel.validateInput(nama, kategori, subkategori)) {
                Toast.makeText(requireContext(), "Lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imageUri == null || itemId == null) {
                Toast.makeText(requireContext(), "Gagal memuat data item!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedItem = SampahItem(
                id = itemId,
                userId = viewModel.getCurrentUserId(),
                title = nama,
                description = deskripsi,
                category = kategori,
                subcategory = subkategori,
                imageUrl = imageUri.toString(),
                isTerbuang = isTerbuang,
                timestamp = System.currentTimeMillis()
            )

            viewModel.updateItem(updatedItem)
        }

        viewModel.editState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setFragmentResult("edit_result", bundleOf("edited" to true))
                    dismiss()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}