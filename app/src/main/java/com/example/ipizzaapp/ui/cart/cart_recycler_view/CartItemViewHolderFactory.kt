package com.example.ipizzaapp.ui.cart.cart_recycler_view

import com.example.ipizzaapp.databinding.CartItemBinding
import com.example.domain.models.Pizza
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CartItemViewHolderFactory {
    fun create (
        itemBinding: CartItemBinding,
        onItemBind: CartItemBinding.(pizza: Pizza) -> Unit
    ): CartItemViewHolder
}