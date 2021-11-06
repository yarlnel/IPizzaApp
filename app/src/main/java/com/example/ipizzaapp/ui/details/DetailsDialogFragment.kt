package com.example.ipizzaapp.ui.details

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding

import com.example.ipizzaapp.databinding.FragmentDetailsDialogBinding
import com.example.ipizzaapp.fragment_lib.Router

import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment
import com.example.ipizzaapp.ui.preview.PreviewFragment
import com.example.ipizzaapp.utils.image_utils.ImageBitmapLoader
import com.example.ipizzaapp.utils.image_utils.setFitImage
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


private const val SELECTED_PIZZA_ID = "selected pizza id parameter"

class DetailsDialogFragment
    : BottomSheetDialogFragment() {
    private val binding: FragmentDetailsDialogBinding
        by viewBinding(CreateMethod.INFLATE)


    private var selectedPizzaId: Int = 0

    @Inject lateinit var modelFactory: ViewModelProvider.Factory
    private val detailsViewModel: DetailsViewModel by viewModels {
        modelFactory
    }

    @Inject lateinit var imageBitmapLoader: ImageBitmapLoader

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        arguments?.let {
           selectedPizzaId = it.getInt(SELECTED_PIZZA_ID)
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
        detailsViewModel.setupPizzaById(selectedPizzaId)
        with(binding) {
            detailsViewModel.selectedPizza.subscribe { pizza ->

                val imageUrl = pizza.imageUrls.first()
                imageBitmapLoader.loadImageBitmap(imageUrl, detailsImageView::setFitImage)

                titleTextView.text = pizza.name
                descriptionTextView.text = pizza.description
                priceTextView.text = "${pizza.price}₽"
            }.let(compositeDisposable::add)

            detailsImageView.setOnClickListener {
                this@DetailsDialogFragment.dismiss()
                detailsViewModel.getSelectedPizzaOrNull()?.let { pizza ->
                    Router.goTo(PreviewFragment.TAG,
                        PreviewFragment.newInstance(pizzaId = pizza.id))
                }
            }

            goToCartFragment.setOnClickListener {
                detailsViewModel.addSelectedPizzaToCart()
                this@DetailsDialogFragment.dismiss()
                Router.goTo(CartFragment.TAG, CartFragment.newInstance())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "Details Dialog Fragment TAG"
        @JvmStatic fun newInstance(pizzaId: Int)
            = DetailsDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(SELECTED_PIZZA_ID, pizzaId)
                }
            }
    }
}