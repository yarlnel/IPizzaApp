package com.example.ipizzaapp.ui.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.FragmentPreviewBinding
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.cart.CartFragment


class PreviewFragment : Fragment(R.layout.fragment_preview) {

    private val router by lazy {
        (requireActivity() as MainActivity)
            .router.from(this@PreviewFragment)
    }

    private val binding by viewBinding(FragmentPreviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            addToCardButton.setOnClickListener {
                router.goTo(CartFragment.TAG, CartFragment.newInstance())
            }
        }
    }

    companion object {  
        const val TAG = "PreviewFragmentTag"
        @JvmStatic fun newInstance() = PreviewFragment()
    }
}