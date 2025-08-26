package com.mokaneko.recycle2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.model.SubKategoriItem
import com.mokaneko.recycle2.ui.kategori.CategoryRecommendationActivity

class SubkategoriAdapter(
    private val items: List<SubKategoriItem>
) : RecyclerView.Adapter<SubkategoriAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaSubKategori: TextView = itemView.findViewById(R.id.sub_title)
        val icon: ImageView = itemView.findViewById(R.id.sub_logo)

        fun bind(item: SubKategoriItem) {
            namaSubKategori.text = item.namaSubKategori
            icon.setImageResource(item.logoResId)

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, CategoryRecommendationActivity::class.java).apply {
                    putExtra("source", "category")
                    putExtra("kategori", item.kategori)
                    putExtra("namaSubKategori", item.namaSubKategori)
                    putExtra("rekomendasi", item.rekomendasi)
                    putExtra("logoResId", item.logoResId)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subkategori, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}
