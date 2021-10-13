package com.example.ipizzaapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ipizzaapp.databinding.FragmentDetailsDialogBinding

import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment
import com.example.ipizzaapp.ui.home.HomeFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailsDialogFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentDetailsDialogBinding? = null
    private val binding get () = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsDialogBinding.inflate(inflater, container, false)
        val fragmentManager = requireActivity().supportFragmentManager
        val router = (requireActivity() as MainActivity).router
        val root = binding.root
        binding.goToCartFragment.setOnClickListener {
            this.dismiss()
            router.goTo(CartFragment.TAG, CartFragment.newInstance())
        }
        return root
    }

    companion object {
        const val TAG = "Details Dialog Fragment TAG"
        @JvmStatic
        fun newInstance() =
            DetailsDialogFragment()
    }
}