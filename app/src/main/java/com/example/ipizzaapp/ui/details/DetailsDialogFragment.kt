package com.example.ipizzaapp.ui.details

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.databinding.FragmentDetailsDialogBinding
import com.example.ipizzaapp.pojo.Pizza

import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment
import com.example.ipizzaapp.ui.preview.PreviewFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso


private const val SELECTED_PIZZA_PARAM = "selected pizza parameter"

class DetailsDialogFragment : BottomSheetDialogFragment() {
    private val binding: FragmentDetailsDialogBinding
        by viewBinding(CreateMethod.INFLATE)

    private val router by lazy {
        (requireActivity() as MainActivity).router
    }

    var selectedPizza : Pizza? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           selectedPizza = it.getParcelable(SELECTED_PIZZA_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View
            = binding.root

    // Адаптируем наш BottomSheetDialog под размеры WRAP_CONTENT
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            setOnShowListener { dialogInterface ->
                val bottomSheetDialog = dialogInterface as BottomSheetDialog
                val bottomSheet = bottomSheetDialog
                    .findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet ?. let {
                    BottomSheetBehavior.from(it).apply {
                        state = BottomSheetBehavior.STATE_EXPANDED
                        skipCollapsed = true
                        isHideable = true
                    }
                }
            }
        }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            selectedPizza ?. let { pizza ->
                Picasso.get()
                    .load(pizza.imageUrls.first())
                    .fit()
                    .into(detailsImageView)

                titleTextView.text = pizza.name
                descriptionTextView.text = pizza.description
                priceTextView.text = "${pizza.price}₽"

            }

            detailsImageView.setOnClickListener {
                this@DetailsDialogFragment.dismiss()
                selectedPizza ?. let { pizza ->
                    router.goTo(PreviewFragment.TAG, PreviewFragment.newInstance(pizza = pizza))
                }
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
                arguments = Bundle().apply {
                    putParcelable(SELECTED_PIZZA_PARAM, pizza)
                }
            }
    }
}