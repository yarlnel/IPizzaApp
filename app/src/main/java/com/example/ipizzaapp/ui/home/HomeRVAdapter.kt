package com.example.ipizzaapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.ContentInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

import com.example.ipizzaapp.databinding.PizzaItemBinding
import com.example.ipizzaapp.similar_db.Pizza
import com.example.ipizzaapp.ui.details.DetailsDialogFragment
import com.squareup.picasso.Picasso


class HomeRecyclerViewAdapter
    (
     private val picasso: Picasso,
     private val fragmentManager: FragmentManager,
    )
    : RecyclerView.Adapter<HomeRecyclerViewAdapter.ItemPizzaViewHolder>() {
    inner class ItemPizzaViewHolder (private val itemBinding: PizzaItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(pizza: Pizza) {
                with(itemBinding) {
                  picasso
                        .load(pizza.imageUrl)
                        .fit()
                        .into(itemImageView)

                    titleTextView.text = pizza.name
                    descriptionTextView.text = "${pizza.description.substring(0..34)}..."
                    priceTextView.text = "${pizza.price}₽"
                    root.setOnClickListener {
                        DetailsDialogFragment
                            .newInstance(pizza = pizza)
                            .show(fragmentManager, DetailsDialogFragment.TAG)
                    }
                }
            }
        }

    private var pizzas = listOf<Pizza>()

    fun setPizzaList(data: List<Pizza>) {
        pizzas = data
        this.notifyDataSetChanged()
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

