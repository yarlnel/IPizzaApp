package com.example.ipizzaapp.ui.cart.cart_recycler_view

import androidx.recyclerview.widget.RecyclerView
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.CartItemBinding
import com.example.ipizzaapp.models.Order
import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.castom_providers.PizzaLoader
import com.example.ipizzaapp.utils.image_utils.ImageBitmapLoader
import com.example.ipizzaapp.utils.image_utils.setFitImage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


class CartItemViewHolder
    @AssistedInject constructor(
        @Assisted private val itemBinding: CartItemBinding,
        @Assisted private val _onItemBind: CartItemBinding.(pizza: Pizza) -> Unit,
        private val pizzaLoader: PizzaLoader,
        private val imageLoader: ImageBitmapLoader
        )
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