package com.example.ipizzaapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ipizzaapp.databinding.FragmentHomeBinding
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment
import com.example.ipizzaapp.ui.preview.PreviewFragment
import com.example.ipizzaapp.ui.details.DetailsDialogFragment


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val router = (requireActivity() as MainActivity).router.from(this)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.btnOpenPreviewFragment.setOnClickListener {
            val previewFragment = PreviewFragment.newInstance()
            router.goTo(PreviewFragment.TAG, previewFragment)
        }

        binding.btnOpenBottomDialog.setOnClickListener {
            DetailsDialogFragment
                .newInstance()
                .show(requireActivity().supportFragmentManager, DetailsDialogFragment.TAG)
        }

        binding.goToCartFragment.setOnClickListener {
            router.goTo(CartFragment.TAG, CartFragment.newInstance())
        }

        return root
    }


    companion object {
        const val TAG = "HomeFragment"

        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}