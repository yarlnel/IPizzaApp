package com.example.ipizzaapp.ui.cart.cart_recycler_view

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.CartItemBinding
import com.example.domain.models.Order
import com.example.domain.models.Pizza
import com.example.domain.usecase.pizza.GetPizzaById
import com.squareup.picasso.Picasso
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.concurrent.schedule


class CartItemViewHolder
    @AssistedInject constructor(
        @Assisted private val itemBinding: CartItemBinding,
        @Assisted private val _onItemBind: CartItemBinding.(pizza: Pizza) -> Unit,
        private val getPizzaById: GetPizzaById,
        private val picasso: Picasso,
        )
    : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(order: Order) : Disposable {
        with(itemBinding) {
            val itemContext = itemBinding.root.context
            getPizzaById(order.pizzaId ?: 1)
            .subscribe (
            { pizza ->
                val imageUrl = pizza.imageUrls.first()

                picasso.load(imageUrl)
                    .error(R.drawable.ic_red_broken)
                    .fit()
                    .into(itemImageView)

                quantityTextView.text = order.quantity.toString()

                titleTextView.text = pizza.name
                priceTextView.text = itemContext
                    .getString(R.string.price_in_currency, pizza.price)

                _onItemBind(pizza)

            }, {
                Log.e("CartItemViewHolder", it.stackTraceToString())
            }).let {
                return@bind it
            }
        }
    }
}