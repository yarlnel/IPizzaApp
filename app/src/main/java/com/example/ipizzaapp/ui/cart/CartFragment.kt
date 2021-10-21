package com.example.ipizzaapp.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.FragmentCartBinding
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.ordered_pizza.PlaceOrderFragment

class CartFragment : Fragment(R.layout.fragment_cart) {
    private val binding by viewBinding(FragmentCartBinding::bind)
    private val router by lazy {
        (requireActivity() as MainActivity)
            .router.from(this@CartFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnGoToOrderedPizzaFragment.setOnClickListener {
                router.goTo(PlaceOrderFragment.TAG, PlaceOrderFragment.newInstance())
            }
        }
    }

    companion object {
        const val TAG = "CartFragmentTag"
        @JvmStatic fun newInstance() = CartFragment()
    }
}