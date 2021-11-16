package com.example.ipizzaapp.ui.home.home_recycler_view

import androidx.recyclerview.widget.RecyclerView
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.PizzaItemBinding
import com.example.ipizzaapp.models.Pizza
import com.squareup.picasso.Picasso
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ItemPizzaViewHolder
    @AssistedInject constructor(
        @Assisted private val itemBinding: PizzaItemBinding,
        @Assisted private val onItemBind: PizzaItemBinding.(pizza: Pizza) -> Unit,
        private val picasso: Picasso,
    ) : RecyclerView.ViewHolder(itemBinding.root) {


    fun bind(pizza: Pizza) {
        with(itemBinding) {
            picasso
                .load(pizza.imageUrls.first())
                .fit()
                .into(itemImageView)

            val itemContext = itemBinding.root.context
            titleTextView.text = pizza.name

            descriptionTextView.text = itemContext.getString(
                R.string.abbreviated_text_placeholder,
                pizza.description.substring(0..34)
            )

            priceTextView.text = itemContext.getString(
                R.string.price_in_currency, pizza.price
            )

            onItemBind(pizza)
        }
    }
}

