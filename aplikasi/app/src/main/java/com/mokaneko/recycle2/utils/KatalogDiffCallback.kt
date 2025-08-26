package com.mokaneko.recycle2.utils

import androidx.recyclerview.widget.DiffUtil
import com.mokaneko.recycle2.data.model.SampahItem

class KatalogDiffCallback : DiffUtil.ItemCallback<SampahItem>() {
    override fun areItemsTheSame(oldItem: SampahItem, newItem: SampahItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SampahItem, newItem: SampahItem): Boolean {
        return oldItem == newItem
    }
}