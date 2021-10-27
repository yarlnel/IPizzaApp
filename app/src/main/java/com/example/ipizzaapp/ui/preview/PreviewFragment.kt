package com.example.ipizzaapp.ui.preview

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.appComponent
import com.example.ipizzaapp.databinding.FragmentPreviewBinding
import com.example.ipizzaapp.network.IPizzaService
import com.example.ipizzaapp.pojo.Pizza
import com.example.ipizzaapp.similar_db.PizzaDatabase
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment
import com.example.ipizzaapp.utils.listeners.OnPageSelected
import com.squareup.picasso.Picasso
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


private const val SELECTED_PIZZA_PARAM = "selected pizza parameter"

class PreviewFragment : Fragment(R.layout.fragment_preview) {

    private val router by lazy {
        (requireActivity() as MainActivity)
            .router.from(this@PreviewFragment)
    }

    private val binding by viewBinding(FragmentPreviewBinding::bind)
    private val previewViewModel : PreviewViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()
    private val viewPagerAdapter = ImageViewPagerAdapter()
    private var selectedPizza: Pizza? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedPizza = it.getParcelable(SELECTED_PIZZA_PARAM)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val countOfImages = selectedPizza?.imageUrls?.count()

        with(binding) {
            pagePositionTextView.text = getString(
                R.string.page_number_to_page_count,
                1, countOfImages
            )

            titleTextView.text = selectedPizza?.name

            priceTextView.text = getString(
                R.string.price_in_currency,
                selectedPizza?.price ?: 0)

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
                router.back()
            }

            addToCartButton.setOnClickListener {
                router.goTo(CartFragment.TAG, CartFragment.newInstance())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {  
        const val TAG = "PreviewFragmentTag"
        @JvmStatic fun newInstance(pizza: Pizza) =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SELECTED_PIZZA_PARAM, pizza)
                }
            }
    }
}