package com.example.ipizzaapp.ui.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizzaapp.databinding.ImageItemBinding
import com.squareup.picasso.Picasso

class ImageViewPagerAdapter : RecyclerView.Adapter<ImageViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: ImageItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
            fun bind (imageUrl: String) {
                with(itemBinding) {
                    Picasso.get()
                        .load(imageUrl)
                        .into(itemImageView)
                }
            }
        }

    private val imageUrlList = mutableListOf<String>()

    fun setImageUrls(imageUrls: List<String>) {
        imageUrlList.clear()
        imageUrlList.addAll(imageUrls)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUrl = imageUrlList[position])
    }

    override fun getItemCount(): Int = imageUrlList.count()
}