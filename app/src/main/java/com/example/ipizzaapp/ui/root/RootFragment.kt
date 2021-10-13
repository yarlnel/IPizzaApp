package com.example.ipizzaapp.ui.root

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ipizzaapp.R
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.home.HomeFragment


class RootFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val router = (requireActivity() as MainActivity).router
        router.from(this).goTo(HomeFragment.TAG, HomeFragment.newInstance())

        return inflater.inflate(R.layout.fragment_root, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RootFragment()
    }
}