package com.example.ipizzaapp.ui.cart.cart_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.ipizzaapp.databinding.CartItemBinding
import com.example.ipizzaapp.models.Order
import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.castom_providers.PizzaLoader
import com.example.ipizzaapp.utils.image_utils.ImageBitmapLoader
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartRecyclerViewAdapter
    @Inject constructor(
        private val picasso: Picasso,
        private val pizzaLoader: PizzaLoader,
        private val imageLoader: ImageBitmapLoader,
        private val cartItemViewHolderFactory: CartItemViewHolderFactory
    ) : RecyclerView.Adapter<CartItemViewHolder>() {
    private var orderList: List<Order> = listOf()

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

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        pizzaLoader.clearDisposable()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}