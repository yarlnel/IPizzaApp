package com.example.ipizzaapp.ui.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ipizzaapp.databinding.FragmentPreviewBinding
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment


class PreviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPreviewBinding.inflate(inflater, container, false)
        val router = (requireActivity() as MainActivity).router
        val root = binding.root
        binding.addToCardButton.setOnClickListener {
            router.from(this).goTo(CartFragment.TAG, CartFragment.newInstance())
        }
        return root
    }

    companion object {
        const val TAG = "PreviewFragmentTag"
        @JvmStatic
        fun newInstance() =
            PreviewFragment()
    }
}