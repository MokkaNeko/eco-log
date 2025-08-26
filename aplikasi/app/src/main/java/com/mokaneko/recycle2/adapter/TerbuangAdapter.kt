package com.mokaneko.recycle2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.model.SampahItem

class TerbuangAdapter(
    private val onDeleteClick: (SampahItem) -> Unit,
    private val onRestoreClick: (SampahItem) -> Unit
) : ListAdapter<SampahItem, TerbuangAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SampahItem>() {
        override fun areItemsTheSame(oldItem: SampahItem, newItem: SampahItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SampahItem, newItem: SampahItem): Boolean = oldItem == newItem
    }
) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.tv_title)
        private val category = view.findViewById<TextView>(R.id.tv_category)
        private val description = view.findViewById<TextView>(R.id.tv_description)
        private val image = view.findViewById<ImageView>(R.id.iv_image)

        private val btnDelete = view.findViewById<View>(R.id.btn_delete)
        private val btnRestore = view.findViewById<View>(R.id.btn_restore)

        fun bind(item: SampahItem) {
            title.text = item.title
            category.text = item.category
            description.text = item.description
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .placeholder(R.drawable.img_placeholder_1x1)
                .into(image)

            btnDelete.setOnClickListener { onDeleteClick(item) }
            btnRestore.setOnClickListener { onRestoreClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_terbuang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
