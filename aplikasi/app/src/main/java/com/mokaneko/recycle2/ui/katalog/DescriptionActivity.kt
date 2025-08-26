package com.mokaneko.recycle2.ui.katalog

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.data.repository.KategoriData
import com.mokaneko.recycle2.databinding.ActivityDescriptionBinding
import com.mokaneko.recycle2.ui.dialog.DialogEditFragment
import com.mokaneko.recycle2.ui.kategori.CategoryRecommendationActivity
import com.mokaneko.recycle2.utils.Resource
import com.mokaneko.recycle2.utils.helper.TopNavHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    private val descViewModel: DescriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<SampahItem>("item") ?: return

        // Tampilkan data
        Glide.with(this).load(item.imageUrl).into(binding.descIvSampah)
        binding.descCategory.text = item.category
        binding.descTvTitle.text = item.title
        binding.descTvContent.text = item.description

        val subkategori = KategoriData.listKategori
            .flatMap { it.subKategoriList }
            .find { it.namaSubKategori.equals(item.subcategory, ignoreCase = true) }
        subkategori?.let {
            binding.descIconCategory.setImageResource(it.logoResId)
        } ?: run {
            binding.descIconCategory.setImageResource(R.drawable.ic_question_mark)
        }

        supportFragmentManager.setFragmentResultListener("edit_result", this) { _, bundle ->
            val isEdited = bundle.getBoolean("edited", false)
            if (isEdited) {
                Toast.makeText(this, "Item berhasil diedit", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        descViewModel.deleteState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.descDeleteConfirm.root.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Item berhasil dihapus", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        descViewModel.moveState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.descTerbuangConfirm.root.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Item berhasil dipindahkan ke sampah terbuang", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        // Setup hamburger menu
        val dropdown = binding.descTopNav.navDropdown
        dropdown.visibility = View.VISIBLE
        dropdown.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.desc_menu, popup.menu)

            val deleteItem = popup.menu.findItem(R.id.desc_menu_delete)
            val redTitle = SpannableString("Hapus sampah")
            redTitle.setSpan(ForegroundColorSpan(Color.RED), 0, redTitle.length, 0)
            redTitle.setSpan(StyleSpan(Typeface.BOLD), 0, redTitle.length, 0)
            deleteItem.title = redTitle

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.desc_menu_delete -> {
                        AlertDialog.Builder(this)
                            .setTitle("Hapus Sampah")
                            .setMessage("Apakah yakin ingin menghapus item ini secara permanen?")
                            .setPositiveButton("Ya") { _, _ ->
                                descViewModel.deleteItem(item)
                            }
                            .setNegativeButton("Batal", null)
                            .show()
                        true
                    }
                    R.id.desc_menu_edit -> {
                        val dialog = DialogEditFragment().apply {
                            arguments = Bundle().apply {
                                putString("id", item.id)
                                putString("image_uri", item.imageUrl)
                                putString("title", item.title)
                                putString("kategori", item.category)
                                putString("subkategori", item.subcategory)
                                putString("description", item.description)
                                putBoolean("isTerbuang", item.isTerbuang)
                            }
                        }
                        dialog.show(supportFragmentManager, "dialogEditFragment")
                        true
                    }
                    R.id.desc_menu_terbuang -> {
                        AlertDialog.Builder(this)
                            .setTitle("Pindahkan ke Sampah Terbuang")
                            .setMessage("Apakah yakin ingin memindahkan item ini ke daftar sampah terbuang?")
                            .setPositiveButton("Ya") { _, _ ->
                                descViewModel.moveToTerbuang(item)
                            }
                            .setNegativeButton("Batal", null)
                            .show()
                        true
                    }
                    R.id.desc_menu_rekomendasi -> {
                        val subkategori = KategoriData.findSubkategori(item.subcategory)
                        if (subkategori != null) {
                            val intent = Intent(this, CategoryRecommendationActivity::class.java).apply {
                                putExtra("source", "description")
                                putExtra("kategori", item.category)
                                putExtra("namaSubKategori", item.subcategory)
                                putExtra("rekomendasi", subkategori.rekomendasi)
                                putExtra("logoResId", subkategori.logoResId)
                                putExtra("imageUrl", item.imageUrl)
                            }
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Subkategori tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        TopNavHelper.setupTopNav(
            activity = this,
            rootView = binding.descTopNav,
            title = "Deskripsi"
        )
    }
}
