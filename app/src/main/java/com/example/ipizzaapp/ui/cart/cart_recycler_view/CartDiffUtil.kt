package com.example.ipizzaapp.ui.cart.cart_recycler_view

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.Order

class CartDiffUtil(
    private val oldList: List<Order>,
    private val newList: List<Order>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition].pizzaId == newList[newItemPosition].pizzaId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition].pizzaId == newList[newItemPosition].pizzaId
}