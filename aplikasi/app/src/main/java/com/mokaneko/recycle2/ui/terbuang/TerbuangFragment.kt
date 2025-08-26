package com.mokaneko.recycle2.ui.terbuang

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.adapter.TerbuangAdapter
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.databinding.FragmentTerbuangBinding
import com.mokaneko.recycle2.utils.SearchableFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TerbuangFragment : Fragment(), SearchableFragment {

    private var _binding: FragmentTerbuangBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TerbuangViewModel by viewModels()

    private lateinit var adapter: TerbuangAdapter

    private var fullList: List<SampahItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTerbuangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_container)
        binding.terbuangFragmentContainer.startAnimation(fadeIn)

        adapter = TerbuangAdapter(
            onDeleteClick = { item ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Hapus Sampah")
                    .setMessage("Apakah yakin ingin menghapus data ini secara permanen?")
                    .setPositiveButton("Ya") { _, _ ->
                        viewModel.deleteTerbuangItem(item)
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            },
            onRestoreClick = { item ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Kembalikan Sampah")
                    .setMessage("Kembalikan data ini ke daftar aktif?")
                    .setPositiveButton("Ya") { _, _ ->
                        viewModel.restoreItemToAktif(item)
                    }
                    .setNegativeButton("Batal", null)
                    .show()
            }
        )

        binding.rvTerbuang.adapter = adapter
        binding.rvTerbuang.layoutManager = LinearLayoutManager(requireContext())

        viewModel.terbuangItems.observe(viewLifecycleOwner) { items ->
            if (items.isNullOrEmpty()) {
                binding.rvTerbuang.visibility = View.GONE
                binding.terbuangDataPlaceholderTitle.visibility = View.VISIBLE
                binding.terbuangDataPlaceholderText.visibility = View.VISIBLE
            } else {
                binding.rvTerbuang.visibility = View.VISIBLE
                binding.terbuangDataPlaceholderTitle.visibility = View.GONE
                binding.terbuangDataPlaceholderText.visibility = View.GONE
                adapter.submitList(items)
            }
        }

        viewModel.terbuangItems.observe(viewLifecycleOwner) { items ->
            fullList = items
            adapter.submitList(items)
        }

        viewModel.eventMessage.observe(viewLifecycleOwner) { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.loadTerbuangItems()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSearchQueryChanged(query: String) {
        val filtered = fullList.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }
        adapter.submitList(filtered)
    }
}
