package com.example.ipizzaapp.ui.preview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.abstractions.BackPressedStrategyOwner
import com.example.ipizzaapp.constants.BundleKeys
import com.example.ipizzaapp.databinding.FragmentPreviewBinding
import com.example.ipizzaapp.fragment_lib.Router
import com.example.ipizzaapp.ui.cart.CartFragment
import com.example.ipizzaapp.ui.preview.preview_view_pager.ImageViewPagerAdapter
import com.example.ipizzaapp.utils.handlers.OnPageSelected
import dagger.Lazy
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject



class PreviewFragment
    : DaggerFragment(R.layout.fragment_preview),
      BackPressedStrategyOwner {

    private val binding by viewBinding(FragmentPreviewBinding::bind)

    @Inject lateinit var modelFactory: Lazy<ViewModelProvider.Factory>
    private val previewViewModel : PreviewViewModel by viewModels {
        modelFactory.get()
    }

    private val compositeDisposable = CompositeDisposable()

    @Inject lateinit var viewPagerAdapter: ImageViewPagerAdapter

    private var pizzaId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pizzaId = it.getInt(BundleKeys.SELECTED_PIZZA_PARAM)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previewViewModel.setupPizzaById(pizzaId)


        with(binding) {
            previewViewModel.selectedPizza.subscribe { selectedPizza ->
                val countOfImages = selectedPizza.imageUrls.count()
                pagePositionTextView.text = getString(
                    R.string.page_number_to_page_count,
                    1, countOfImages
                )

                titleTextView.text = selectedPizza?.name

                priceTextView.text = getString(
                    R.string.price_in_currency,
                    selectedPizza?.price ?: 0
                )

                viewPagerAdapter.setImageUrls(
                    imageUrls = selectedPizza?.imageUrls ?: listOf()
                )

                imageViewPager.registerOnPageChangeCallback(OnPageSelected { pageNumber ->
                    previewViewModel.setCurrentPageNumber(pageNumber)
                })

                previewViewModel.currentPageNumber.subscribe { pageNumber ->
                    pagePositionTextView.text = getString(
                        R.string.page_number_to_page_count,
                        pageNumber, countOfImages
                    )
                }.let(compositeDisposable::add)

                imageViewPager.adapter = viewPagerAdapter
                backImageContainer.setOnClickListener {
                    Router.back()
                }

                addToCartButton.setOnClickListener {
                    previewViewModel.addSelectedPizzaToCart()
                    Router.goTo(CartFragment.TAG, CartFragment.newInstance())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {  
        const val TAG = "PreviewFragmentTag"
        @JvmStatic fun newInstance(pizzaId: Int) =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    putInt(BundleKeys.SELECTED_PIZZA_PARAM, pizzaId)
                }
            }
    }

    override fun customBackPressedHandlerFunction() {
        Router.back()
    }
}