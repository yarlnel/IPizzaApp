package com.example.ipizzaapp.ui.ordered_pizza

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ipizzaapp.databinding.FragmentPlaceOrderBinding
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.home.HomeFragment


class PlaceOrderFragment : Fragment() {
    private var _binding : FragmentPlaceOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceOrderBinding.inflate(inflater, container, false)
        val root = binding.root
        val router = (requireActivity() as MainActivity).router.from(this@PlaceOrderFragment)
        val fragmentManager = requireActivity().supportFragmentManager
        binding.backToMenuButton.setOnClickListener {
            router.backTo(HomeFragment.TAG)
        //router.backTo(HomeFragment.TAG)
        }
        return root
    }

    companion object {
        const val TAG = "OrderedPizzaFragment"
        @JvmStatic
        fun newInstance() =
            PlaceOrderFragment()
    }
}