package com.mokaneko.recycle2.data.model

import androidx.annotation.DrawableRes

data class SubKategoriItem(
    val namaSubKategori: String,
    val kategori: String,
    val rekomendasi: String,
    @DrawableRes val logoResId: Int
)
