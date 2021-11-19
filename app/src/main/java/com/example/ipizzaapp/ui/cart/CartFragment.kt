package com.example.ipizzaapp.ui.cart

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.snackbar.Snackbar
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
                cartViewModel.placeOrder(
                    onError = { err ->
                        Snackbar
                            .make(binding.root,
                                """|Походу у вас проблемы с интернетом :(
                                   |Не удалось отправить заказ...
                                   |Повторите попытку позже
                                """.trimMargin(),
                                Snackbar.LENGTH_LONG)
                            .show()
                        Log.e(javaClass.simpleName, """
                            PlaceOrder Error
                            Stacktrace:
                            ${err.stackTraceToString()}
                        """.trimIndent())
                    },
                    onSuccess = {
                        Router.goTo(
                            PlaceOrderFragment.TAG,
                            PlaceOrderFragment.newInstance())
                        cartViewModel.clearOrders()
                    }
                )


            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        cartRecyclerViewAdapter.compositeDisposable.clear()
    }

    companion object {
        const val TAG = "CartFragmentTag"
        @JvmStatic fun newInstance() = CartFragment()
    }
}