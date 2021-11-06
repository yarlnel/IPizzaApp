package com.example.ipizzaapp.ui.ordered_pizza

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.FragmentPlaceOrderBinding
import com.example.ipizzaapp.fragment_lib.Router
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.home.HomeFragment
import com.google.gson.Gson
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class PlaceOrderFragment : DaggerFragment(R.layout.fragment_place_order) {
    private val binding by viewBinding(FragmentPlaceOrderBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            backToMenuButton.setOnClickListener {
                Router.backTo(HomeFragment.TAG)
            }
        }
    }

    companion object {
        const val TAG = "OrderedPizzaFragment"
        @JvmStatic fun newInstance() = PlaceOrderFragment()
    }
}