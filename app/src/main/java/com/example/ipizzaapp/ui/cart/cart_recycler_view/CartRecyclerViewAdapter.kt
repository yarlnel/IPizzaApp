package com.example.ipizzaapp.ui.cart.cart_recycler_view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.ipizzaapp.databinding.CartItemBinding
import com.example.domain.models.Order
import com.example.domain.models.Pizza
import com.example.domain.usecase.pizza.GetAllPizza
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Inject

class CartRecyclerViewAdapter
    @Inject constructor(
        private val cartItemViewHolderFactory: CartItemViewHolderFactory,
        private val getAllPizza: GetAllPizza,
    ) : RecyclerView.Adapter<CartItemViewHolder>() {
    private var orderList: List<Order> = emptyList()
    val compositeDisposable = CompositeDisposable()

    private var _onItemBind: CartItemBinding.(pizza: Pizza) -> Unit = {}
    fun onItemBind(itemBindCallback: CartItemBinding.(pizza: Pizza) -> Unit) {
        _onItemBind = itemBindCallback
    }

    fun setItems (newOrderList: List<Order>) {
        val homeDiffUtil = CartDiffUtil(orderList, newOrderList)
        val diffResult = DiffUtil.calculateDiff(homeDiffUtil)
        orderList = newOrderList
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding = CartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return cartItemViewHolderFactory.create(
            itemBinding = binding,
            onItemBind = _onItemBind,
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int = orderList.count()
}