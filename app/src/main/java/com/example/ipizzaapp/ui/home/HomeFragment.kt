package com.example.ipizzaapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.abstractions.BackPressedStrategyOwner
import com.example.ipizzaapp.databinding.FragmentHomeBinding
import com.example.ipizzaapp.similar_db.PizzaDatabase
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.utils.custom_managers.CustomSoftKeyboardManager
import com.example.ipizzaapp.utils.listeners.OnEnterKeyPressed
import com.example.ipizzaapp.utils.listeners.OnTextChange
import com.squareup.picasso.Picasso


class HomeFragment : Fragment(R.layout.fragment_home), BackPressedStrategyOwner {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val router by lazy {
        (requireActivity() as MainActivity)
            .router.from(this@HomeFragment)
    }

    private val homeViewModel : HomeViewModel by viewModels {
        HomeViewModelFactory(PizzaDatabase.pizzaDao)
    }

    private val homeRecyclerViewAdapter by lazy {
        HomeRecyclerViewAdapter(
            picasso = Picasso.get(),
            fragmentManager = requireActivity().supportFragmentManager
        )
    }
    private val keyboardManager by lazy {
        CustomSoftKeyboardManager(requireActivity() as AppCompatActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            homeRecyclerView.adapter = homeRecyclerViewAdapter

            searchView.addTextChangedListener(OnTextChange { text ->
                if (text.isEmpty()) {
                    homeViewModel.stopSearchMode()
                }
                homeViewModel.filterPizzaByName(text)
            })

            searchView.setOnEditorActionListener(OnEnterKeyPressed { text ->
                homeViewModel.filterPizzaByName(text)
                keyboardManager.hideKeyboard()
            })

            searchView.setOnClickListener {
                homeViewModel.startSearchMode()
            }

            searchImageView.setOnClickListener {
                homeViewModel.startSearchMode()
            }

            // Да костыль, можно было-бы сделать и через toolbar
            // А не через замену видимости элементов
            // Но решение через Toolbar трудно кастомизировать
            // С использованием SearchView аналогичные проблеммы
            homeViewModel.appBarConfig.observe(viewLifecycleOwner, Observer { toolbarConfig ->
                when (toolbarConfig ?: HomeAppBarConfig.BASE_MODE) {
                    HomeAppBarConfig.BASE_MODE -> showMenuContainer()
                    HomeAppBarConfig.SEARCH_MODE -> showSearchContainer()
                }
            })


        }

        homeViewModel.pizzaList.observe(viewLifecycleOwner, Observer { pizzas ->
            homeRecyclerViewAdapter.setPizzaList(pizzas)
        })
    }


    private fun FragmentHomeBinding.showMenuContainer() {
        menuContainer.visibility = View.VISIBLE
        searchContainer.visibility = View.INVISIBLE
        searchView.isEnabled = false
        keyboardManager.hideKeyboard()
    }



    private fun FragmentHomeBinding.showSearchContainer() {
        menuContainer.visibility = View.INVISIBLE
        searchContainer.visibility = View.VISIBLE
        searchView.isEnabled = true
        keyboardManager.showKeyboardAndSetFocus(focusEditText = searchView)
    }

    companion object {
        const val TAG = "HomeFragment"
        @JvmStatic fun newInstance() = HomeFragment()
    }

    override fun customBackPressedHandlerFunction() {
        requireActivity().apply {
            if (homeViewModel.appBarConfig.value == HomeAppBarConfig.SEARCH_MODE) {
                homeViewModel.stopSearchMode()
            } else {
                supportFragmentManager.popBackStack()
                supportFragmentManager.popBackStack()
                (this as MainActivity).parentBackPressedFunction()
            }
        }
    }
}

