package com.example.ipizzaapp.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.FragmentCartBinding
import com.example.ipizzaapp.fragment_lib.Router
import com.example.ipizzaapp.ui.cart.cart_recycler_view.CartRecyclerViewAdapter
import com.example.ipizzaapp.ui.ordered_pizza.PlaceOrderFragment
import dagger.Lazy
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CartFragment : DaggerFragment(R.layout.fragment_cart) {
    private val binding by viewBinding(FragmentCartBinding::bind)

    @Inject lateinit var  cartRecyclerViewAdapter: CartRecyclerViewAdapter
    @Inject lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>
    private val cartViewModel : CartViewModel by viewModels {
        viewModelFactory.get()
    }

    private val compositeDisposable = CompositeDisposable()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartRecyclerViewAdapter.onItemBind { pizza ->
            cartViewModel.quantityById(pizza.id).subscribe { quantity ->
                quantityTextView.text = quantity.toString()
            }.let(compositeDisposable::add)

            plusButton.setOnClickListener {
                cartViewModel.addOnePizza(pizza.id)
            }

            minusButton.setOnClickListener {
                cartViewModel.removeOnePizza(pizza.id)
            }
        }

        cartViewModel.orderList
            .subscribe(cartRecyclerViewAdapter::setItems)
            .let(compositeDisposable::add)

        with(binding) {
            cartRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            cartRecyclerView.adapter = cartRecyclerViewAdapter
            clearOrderButton.setOnClickListener {
                cartViewModel.clearOrders()
            }
            btnGoToOrderedPizzaFragment.setOnClickListener {
                Router.goTo(PlaceOrderFragment.TAG, PlaceOrderFragment.newInstance())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "CartFragmentTag"
        @JvmStatic fun newInstance() = CartFragment()
    }
}