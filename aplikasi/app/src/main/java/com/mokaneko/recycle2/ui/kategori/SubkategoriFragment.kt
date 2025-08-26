package com.mokaneko.recycle2.ui.kategori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mokaneko.recycle2.adapter.SubkategoriAdapter
import com.mokaneko.recycle2.data.repository.KategoriData
import com.mokaneko.recycle2.databinding.FragmentSubkategoriBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubkategoriFragment : Fragment() {

    private var _binding: FragmentSubkategoriBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SubkategoriAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubkategoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val kategori = arguments?.getString("kategori") ?: return
        val subKategoriList = KategoriData.listKategori
            .find { it.namaKategori == kategori }
            ?.subKategoriList ?: emptyList()

        adapter = SubkategoriAdapter(subKategoriList)
        binding.rvSubkategori.adapter = adapter
        binding.rvSubkategori.layoutManager = LinearLayoutManager(requireContext())
    }
}