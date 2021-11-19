package com.example.ipizzaapp.ui.home.home_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Pizza

import com.example.ipizzaapp.databinding.PizzaItemBinding
import javax.inject.Inject


class HomeRecyclerViewAdapter
    @Inject constructor(
        private val itemPizzaViewHolderFactory: ItemPizzaViewHolderFactory
    ) : RecyclerView.Adapter<ItemPizzaViewHolder>() {

    private var _onItemBind: PizzaItemBinding.(pizza: Pizza) -> Unit = {}
    fun onItemBind(itemBindCallback: PizzaItemBinding.(pizza: Pizza) -> Unit) {
        _onItemBind = itemBindCallback
    }

    private var pizzas = emptyList<Pizza>()

    fun setPizzaList(newPizzas: List<Pizza>) {
        val homeDiffUtil = HomeDiffUtil(pizzas, newPizzas)
        val diffResult = DiffUtil.calculateDiff(homeDiffUtil)
        pizzas = newPizzas
        diffResult.dispatchUpdatesTo(this)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPizzaViewHolder {
        val binding = PizzaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return itemPizzaViewHolderFactory.create(
            itemBinding = binding,
            onItemBind = _onItemBind
        )
    }

    override fun onBindViewHolder(holder: ItemPizzaViewHolder, position: Int) {
       holder.bind(pizzas[position])
    }

    override fun getItemCount(): Int
        = pizzas.size


}

