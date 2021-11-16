package com.example.ipizzaapp.ui.preview.preview_view_pager

import com.example.ipizzaapp.databinding.ImageItemBinding
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ImagePageViewHolderFactory {
    fun create(itemBinding: ImageItemBinding) : ImagePageViewHolder
}