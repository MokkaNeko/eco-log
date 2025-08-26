package com.mokaneko.recycle2.data.model

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class SampahItem(
    var id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val subcategory: String = "",
    val imageUrl: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    var isTerbuang: Boolean = false
) : Parcelable
