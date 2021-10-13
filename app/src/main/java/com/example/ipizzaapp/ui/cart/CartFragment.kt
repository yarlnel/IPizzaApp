package com.example.ipizzaapp.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ipizzaapp.databinding.FragmentCartBinding
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.ordered_pizza.PlaceOrderFragment

class CartFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCartBinding.inflate(inflater, container, false)
        val router = (requireActivity() as MainActivity).router.from(this@CartFragment)
        val root = binding.root
        binding.btnGoToOrderedPizzaFragment.setOnClickListener {
            router.goTo(PlaceOrderFragment.TAG, PlaceOrderFragment.newInstance())
        }
        return root
    }

    companion object {
        const val TAG = "CartFragmentTag"

        @JvmStatic
        fun newInstance() =
            CartFragment()
    }
}