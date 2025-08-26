package com.mokaneko.recycle2.ui.kategori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.databinding.FragmentKategoriBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class KategoriFragment : Fragment() {

    private var _binding: FragmentKategoriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKategoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_container)
        binding.kategoriContainer.startAnimation(fadeIn)

        binding.kategoriOrganik.setOnClickListener {
            navigateToSubkategori("Organik")
        }

        binding.kategoriAnorganik.setOnClickListener {
            navigateToSubkategori("Anorganik")
        }

        binding.kategoriB3.setOnClickListener {
            navigateToSubkategori("B3")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToSubkategori(kategori: String) {
        val fragment = SubkategoriFragment().apply {
            arguments = bundleOf("kategori" to kategori)
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}