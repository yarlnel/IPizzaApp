package com.example.ipizzaapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizzaapp.R

import com.example.ipizzaapp.databinding.CartItemBinding
import com.example.ipizzaapp.models.CartItemModel
import com.example.ipizzaapp.models.Order
import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.castom_providers.PizzaLoader
import com.example.ipizzaapp.utils.image_utils.ImageBitmapLoader
import com.example.ipizzaapp.utils.image_utils.setFitImage
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartRecyclerViewAdapter
    @Inject constructor(
        private val picasso: Picasso,
        private val pizzaLoader: PizzaLoader,
        private val imageLoader: ImageBitmapLoader,
    ) : RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder>() {
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

    inner class ViewHolder
        (private val itemBinding: CartItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(order: Order) {
            with(itemBinding) {
                val itemContext = itemBinding.root.context
                pizzaLoader.loadPizzaById(order.pizzaId ?: 1) { pizza ->
                    val imageUrl = pizza.imageUrls.first()
                    imageLoader.loadImageBitmap(imageUrl) { bitmap ->
                        itemImageView.setFitImage(bitmap)
                    }

                    quantityTextView.text = order.quantity.toString()

                    titleTextView.text = pizza.name
                    priceTextView.text = itemContext
                        .getString(R.string.price_in_currency, pizza.price)

                    _onItemBind(pizza)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int = orderList.count()

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        pizzaLoader.clearDisposable()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}