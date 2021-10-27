package com.example.ipizzaapp.ui.ordered_pizza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.FragmentPlaceOrderBinding
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.home.HomeFragment



class PlaceOrderFragment : Fragment(R.layout.fragment_place_order) {
    private val binding by viewBinding(FragmentPlaceOrderBinding::bind)

    private val router by lazy {
        (requireActivity() as MainActivity)
            .router.from(this@PlaceOrderFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            backToMenuButton.setOnClickListener {
                router.backTo(HomeFragment.TAG)
            }
        }
    }

    companion object {
        const val TAG = "OrderedPizzaFragment"
        @JvmStatic fun newInstance() = PlaceOrderFragment()
    }
}