package com.mokaneko.recycle2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.utils.KatalogDiffCallback

class KatalogAdapter(
    private val onItemClick: (SampahItem) -> Unit
) : ListAdapter<SampahItem, KatalogAdapter.KatalogViewHolder>(KatalogDiffCallback()) {

    inner class KatalogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.katalog_item_image)
        private val title: TextView = view.findViewById(R.id.katalog_item_title)
        private val category: TextView = view.findViewById(R.id.katalog_item_category)
        private val desc: TextView = view.findViewById(R.id.katalog_item_description)

        fun bind(item: SampahItem) {
            title.text = item.title
            category.text = item.category
            desc.text = item.description
            Glide.with(itemView).load(item.imageUrl).into(image)

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KatalogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_katalog, parent, false)
        return KatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: KatalogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}