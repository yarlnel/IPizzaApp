package com.example.ipizzaapp.ui.preview.preview_view_pager

import androidx.recyclerview.widget.RecyclerView
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.ImageItemBinding
import com.squareup.picasso.Picasso
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ImagePageViewHolder
    @AssistedInject constructor(
        @Assisted private val itemBinding: ImageItemBinding,
        private val picasso: Picasso,
    ) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind (imageUrl: String) {
        with(itemBinding) {
            picasso
                .load(imageUrl)
                .error(R.drawable.ic_red_broken)
                .into(itemImageView)
        }
    }
}