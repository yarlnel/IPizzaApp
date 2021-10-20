package com.example.ipizzaapp.ui.home

import android.app.Activity
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.databinding.FragmentHomeBinding
import com.example.ipizzaapp.listeners.OnTextChangeListener
import com.example.ipizzaapp.similar_db.PizzaDatabase
import com.example.ipizzaapp.ui.MainActivity
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val router by lazy {
        (requireActivity() as MainActivity)
            .router.from(this@HomeFragment)
    }
    private val homeViewModel : HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(pizzaDao = PizzaDatabase.pizzaDao
            )).get(HomeViewModel::class.java)
    }

    private val homeRecyclerViewAdapter by lazy {
        HomeRecyclerViewAdapter(
            picasso = Picasso.get(),
            fragmentManager = requireActivity().supportFragmentManager
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            homeRecyclerView.adapter = homeRecyclerViewAdapter

            // set search icon into searchView
            searchView.setCompoundDrawables(
                null,
                null,
                ContextCompat
                    .getDrawable(requireContext(), R.drawable.ic_search_black),
                null)

            searchView.addTextChangedListener(OnTextChangeListener { text ->
                if (text.isEmpty()) {
                    homeViewModel.stopSearchMode()
                }
                homeViewModel.filterPizzaByName(text)
            })

            searchImageView.setOnClickListener {
                homeViewModel.startSearchMode()
            }

            homeViewModel.appBarConfig.observe(viewLifecycleOwner, Observer { toolbarConfig ->
                when (toolbarConfig) {
                    HomeAppBarConfig.BASE_MODE -> {
                        menuItem.visibility = View.VISIBLE
                        searchView.visibility = View.INVISIBLE
                        (requireActivity()
                            .getSystemService(Activity.INPUT_METHOD_SERVICE)
                            as? InputMethodManager
                        )?.hideSoftInputFromWindow(
                                requireActivity().currentFocus?.windowToken,
                                0
                            )

                    }
                    HomeAppBarConfig.SEARCH_MODE -> {
                        menuItem.visibility = View.INVISIBLE
                        searchView.visibility = View.VISIBLE
                        searchView.requestFocus()
                    }
                }
            })
        }

        homeViewModel.pizzaList.observe(viewLifecycleOwner, Observer { pizzas ->
            homeRecyclerViewAdapter.setPizzaList(pizzas)
        })
    }

    companion object {
        const val TAG = "HomeFragment"
        @JvmStatic fun newInstance() = HomeFragment()
    }
}

