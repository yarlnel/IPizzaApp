package com.example.ipizzaapp.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.example.ipizzaapp.pojo.Pizza

class HomeDiffUtil(
    private val oldList: List<Pizza>,
    private val newList: List<Pizza>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
        = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return  oldItem.imageUrls == newItem.imageUrls &&
                oldItem.name == newItem.name &&
                oldItem.price == newItem.price
    }
}