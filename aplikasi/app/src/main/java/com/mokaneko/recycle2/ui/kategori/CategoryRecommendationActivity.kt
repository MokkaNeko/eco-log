package com.mokaneko.recycle2.ui.kategori

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.databinding.ActivityCategoryRecommendationBinding
import com.mokaneko.recycle2.utils.helper.TopNavHelper

class CategoryRecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TopNavHelper.setupTopNav(
            activity = this,
            rootView = binding.recTopNav,
            title = "Rekomendasi"
        )

        val source = intent.getStringExtra("source") ?: "category"
        val kategori = intent.getStringExtra("kategori") ?: ""
        val namaSubKategori = intent.getStringExtra("namaSubKategori") ?: ""
        val rekomendasi = intent.getStringExtra("rekomendasi") ?: ""
        val logoResId = intent.getIntExtra("logoResId", R.drawable.img_placeholder_1x1)
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""

        binding.recTextCategory.text = kategori
        binding.recTvTitle.text = namaSubKategori
        binding.recTvContent.text = formatRekomendasiText(rekomendasi)

        if (source == "description") {
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.img_placeholder_1x1)
                .into(binding.recIvSampah)
            binding.recIconCategory.setImageResource(logoResId)
        } else {
            binding.recIvSampah.setImageResource(logoResId)
            binding.recIvSampah.setColorFilter(
                ContextCompat.getColor(this, R.color.black),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            when (kategori) {
                "Organik" -> binding.recIconCategory.setImageResource(R.drawable.new_icon_category_organic)
                "Anorganik" -> binding.recIconCategory.setImageResource(R.drawable.new_icon_category_anorganic)
                "B3" -> binding.recIconCategory.setImageResource(R.drawable.new_icon_category_b3)
            }
        }
    }

    private fun formatRekomendasiText(text: String): SpannableString {
        val spannable = SpannableString(text)
        val keyword = "Rekomendasi Pengolahan:"

        var startIndex = text.indexOf(keyword)
        while (startIndex != -1) {
            val endIndex = startIndex + keyword.length
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                RelativeSizeSpan(1.1f),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            startIndex = text.indexOf(keyword, endIndex)
        }
        return spannable
    }
}