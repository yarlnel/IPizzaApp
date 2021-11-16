package com.example.ipizzaapp.ui.preview.preview_view_pager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizzaapp.databinding.ImageItemBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImageViewPagerAdapter
    @Inject constructor(
        private val imagePageViewHolderFactory: ImagePageViewHolderFactory,
    ) : RecyclerView.Adapter<ImagePageViewHolder>() {
    private val imageUrlList = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setImageUrls(imageUrls: List<String>) {
        imageUrlList.clear()
        imageUrlList.addAll(imageUrls)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePageViewHolder {
        val binding = ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return imagePageViewHolderFactory.create(itemBinding = binding)
    }

    override fun onBindViewHolder(holder: ImagePageViewHolder, position: Int) {
        holder.bind(imageUrl = imageUrlList[position])
    }

    override fun getItemCount(): Int = imageUrlList.count()
}