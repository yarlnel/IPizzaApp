package com.example.ipizzaapp.ui.root

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ipizzaapp.R
import com.example.ipizzaapp.fragment_lib.Router
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.home.HomeFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class RootFragment : DaggerFragment(R.layout.fragment_root) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Router.from(this).goTo(HomeFragment.TAG, HomeFragment.newInstance())
    }

    companion object {
        const val TAG = "RootFragmentTag"
        @JvmStatic fun newInstance() = RootFragment()
    }
}