package com.example.ipizzaapp.ui.root

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ipizzaapp.R
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.home.HomeFragment


class RootFragment : Fragment(R.layout.fragment_root) {
    private val router by lazy {
        (requireActivity() as MainActivity).router.from(this@RootFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router.from(this).goTo(HomeFragment.TAG, HomeFragment.newInstance())
    }

    companion object {
        const val TAG = "RootFragmentTag"
        @JvmStatic fun newInstance() = RootFragment()
    }
}