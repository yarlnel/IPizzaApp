package com.example.ipizzaapp.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding

import com.example.ipizzaapp.databinding.FragmentDetailsDialogBinding
import com.example.ipizzaapp.image_utils.ImageRoundedCornersTransformation
import com.example.ipizzaapp.similar_db.Pizza

import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso


class DetailsDialogFragment : BottomSheetDialogFragment() {
    private val binding: FragmentDetailsDialogBinding
        by viewBinding(CreateMethod.INFLATE)

    private val router by lazy {
        (requireActivity() as MainActivity).router
    }

    var selectedPizza : Pizza? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View
            = binding.root


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            selectedPizza ?. let { pizza ->
                Picasso.get()
                    .load(pizza.imageUrl)
                    .fit()
                    .transform(ImageRoundedCornersTransformation(radius = 16))
                    .into(detailsImageView)

                titleTextView.text = pizza.name
                descriptionTextView.text = pizza.description
                priceTextView.text = "${pizza.price}₽"

            }
            goToCartFragment.setOnClickListener {
                this@DetailsDialogFragment.dismiss()
                router.goTo(CartFragment.TAG, CartFragment.newInstance())
            }
        }
    }

    companion object {
        const val TAG = "Details Dialog Fragment TAG"
        @JvmStatic fun newInstance(pizza: Pizza)
            = DetailsDialogFragment().apply {
                selectedPizza = pizza
            }
    }
}