package com.mokaneko.recycle2.ui.katalog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.adapter.KatalogAdapter
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.databinding.FragmentKatalogBinding
import com.mokaneko.recycle2.utils.SearchableFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KatalogFragment : Fragment(), SearchableFragment {

    private var _binding: FragmentKatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: KatalogViewModel by viewModels()
    private lateinit var adapter: KatalogAdapter

    private var fullList: List<SampahItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_container)
        binding.katalogFragmentContainer.startAnimation(fadeIn)

        adapter = KatalogAdapter { item ->
            val intent = Intent(requireContext(), DescriptionActivity::class.java)
            intent.putExtra("item", item)
            startActivity(intent)
        }
        binding.rvKatalog.adapter = adapter
        binding.rvKatalog.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.items.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                binding.rvKatalog.visibility = View.GONE
                binding.katalogDataPlaceholderTitle.visibility = View.VISIBLE
                binding.katalogDataPlaceholderText.visibility = View.VISIBLE
            } else {
                adapter.submitList(list)
                binding.rvKatalog.visibility = View.VISIBLE
                binding.katalogDataPlaceholderTitle.visibility = View.GONE
                binding.katalogDataPlaceholderText.visibility = View.GONE
            }
        }

        viewModel.items.observe(viewLifecycleOwner) { list ->
            fullList = list
            adapter.submitList(list)
        }

        viewModel.loadUserTrashItems()
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