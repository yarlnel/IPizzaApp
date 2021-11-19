package com.example.ipizzaapp.ui.home.home_recycler_view

import com.example.ipizzaapp.databinding.PizzaItemBinding
import com.example.domain.models.Pizza
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ItemPizzaViewHolderFactory {
    fun create (
        itemBinding: PizzaItemBinding,
        onItemBind: PizzaItemBinding.(pizza: Pizza) -> Unit,
    ) : ItemPizzaViewHolder
}