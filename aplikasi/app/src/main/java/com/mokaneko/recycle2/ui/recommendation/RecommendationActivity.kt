package com.mokaneko.recycle2.ui.recommendation

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.repository.KategoriData
import com.mokaneko.recycle2.databinding.ActivityRecommendationBinding
import com.mokaneko.recycle2.ui.dialog.DialogItemFragment
import com.mokaneko.recycle2.ui.kamera.CameraActivity
import com.mokaneko.recycle2.utils.helper.TopNavHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra("image_uri")?.let { Uri.parse(it) }
        val label = intent.getStringExtra("label")
        val confidence = intent.getFloatExtra("confidence", 0f)

        imageUri?.let {
            binding.recIvSampah.setImageURI(it)
        }

        if (confidence < 80f) {
            showUnrecognizedDialog(imageUri)
            binding.recIconCategory.setImageResource(R.drawable.ic_question_mark)
            binding.recTextCategory.text = "Tidak Diketahui"
            binding.recTvTitle.text = "Item ini tidak diketahui"
            binding.recConfidence.visibility = View.GONE
            binding.recTvContent.visibility = View.GONE
        } else {
            val subkategori = KategoriData.listKategori
                .flatMap { it.subKategoriList }
                .find { it.namaSubKategori.equals(label, ignoreCase = true) }

            subkategori?.let {
                binding.recTextCategory.text = it.kategori
                binding.recTvTitle.text = it.namaSubKategori
                binding.recTvContent.text = formatRekomendasiText(it.rekomendasi)
                binding.recIconCategory.setImageResource(it.logoResId)
                binding.recConfidence.text = "Confidence: %.2f%%".format(confidence)
            }
        }
        binding.recFabAdd.setOnClickListener {
            val dialog = DialogItemFragment().apply {
                arguments = Bundle().apply {
                    putString("image_uri", imageUri.toString())
                    putString("kategori", binding.recTextCategory.text.toString())
                    putString("subkategori", binding.recTvTitle.text.toString())
                }
            }
            dialog.show(supportFragmentManager, "DialogAddItemFragment")
        }

        TopNavHelper.setupTopNav(
            activity = this,
            rootView = binding.recTopNav,
            title = "Rekomendasi"
        )
    }

    private fun showUnrecognizedDialog(imageUri: Uri?) {
        AlertDialog.Builder(this)
            .setTitle("Item Tidak Dikenali")
            .setMessage("Apakah kamu ingin tetap menyimpannya?")
            .setPositiveButton("Ya") { _, _ ->
                val dialog = DialogItemFragment().apply {
                    arguments = Bundle().apply {
                        putString("image_uri", imageUri.toString())
                    }
                }
                dialog.show(supportFragmentManager, "DialogAddItemFragment")
                supportFragmentManager.addOnBackStackChangedListener {
                    if (dialog.dialog == null || !dialog.dialog!!.isShowing) {
                        startActivity(Intent(this, CameraActivity::class.java))
                        finish()
                    }
                }
            }
            .setNegativeButton("Tidak") { _, _ ->
                startActivity(Intent(this, CameraActivity::class.java))
                finish()
            }
            .setCancelable(false)
            .show()
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