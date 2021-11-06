package com.example.ipizzaapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ipizzaapp.R

import com.example.ipizzaapp.databinding.PizzaItemBinding
import com.example.ipizzaapp.models.Pizza
import com.squareup.picasso.Picasso
import javax.inject.Inject


class HomeRecyclerViewAdapter
    @Inject constructor(private val picasso: Picasso)
    : RecyclerView.Adapter<HomeRecyclerViewAdapter.ItemPizzaViewHolder>() {
    inner class ItemPizzaViewHolder (private val itemBinding: PizzaItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

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

                    root.setOnClickListener {
                        onRootElementClick(pizza)
                    }

                    itemImageView.setOnClickListener {
                        onImageItemClick(pizza)
                    }
                }
            }
        }

    var onRootElementClick: (pizza: Pizza) -> Unit = {}
    var onImageItemClick: (pizza: Pizza) -> Unit = {}




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
        return ItemPizzaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemPizzaViewHolder, position: Int) {
       holder.bind(pizzas[position])
    }

    override fun getItemCount(): Int
        = pizzas.size


}

