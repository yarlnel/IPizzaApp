package com.example.ipizzaapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.abstractions.BackPressedStrategyOwner

import com.example.ipizzaapp.databinding.FragmentHomeBinding
import com.example.domain.models.Pizza
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.details.DetailsDialogFragment
import com.example.ipizzaapp.ui.home.home_recycler_view.HomeRecyclerViewAdapter
import com.example.ipizzaapp.utils.custom_managers.keyboard.CustomKeyboardManagerFactory
import com.example.ipizzaapp.utils.handlers.OnEnterKeyPressed
import dagger.Lazy
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class HomeFragment : DaggerFragment(R.layout.fragment_home), BackPressedStrategyOwner {
    private val binding by viewBinding(FragmentHomeBinding::bind)


    @Inject lateinit var modelFactory: Lazy<ViewModelProvider.Factory>
    private val homeViewModel : HomeViewModel by viewModels {
        modelFactory.get()
    }

    @Inject lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter



    @Inject lateinit var customKeyboardManagerFactory: CustomKeyboardManagerFactory
    private val keyboardManager by lazy {
        customKeyboardManagerFactory
            .create(requireActivity() as AppCompatActivity)
    }


    private val compositeDisposable = CompositeDisposable()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.setupPizza()

        keyboardManager

        homeRecyclerViewAdapter.onItemBind { pizza ->
            root.setOnClickListener {
                showDetailsDialog(pizza = pizza)
                keyboardManager.hideKeyboard()
            }
        }

        with(binding) {
            homeRecyclerView.adapter = homeRecyclerViewAdapter


            searchView.addTextChangedListener { text ->
                homeViewModel.setSearchText(text.toString())
            }


            searchView.setOnEditorActionListener(OnEnterKeyPressed { text ->
                homeViewModel.setSearchText(text)
                keyboardManager.hideKeyboard()
            })

            searchView.setOnClickListener {
                homeViewModel.startSearchMode()
            }

            searchImageView.setOnClickListener {
                homeViewModel.startSearchMode()
            }
            menuContainer.setOnClickListener {
                homeViewModel.startSearchMode()
            }

            // Да костыль, можно было-бы сделать и через toolbar
            // А не через замену видимости элементов
            // Но решение через Toolbar трудно кастомизировать
            // С использованием SearchView аналогичные проблеммы
            homeViewModel.appBarConfig.subscribe { toolbarConfig ->
                when (toolbarConfig ?: HomeAppBarConfig.BASE_MODE) {
                    HomeAppBarConfig.BASE_MODE -> showMenuContainer()
                    HomeAppBarConfig.SEARCH_MODE -> showSearchContainer()
                }
            }.let(compositeDisposable::add)


        }

        homeViewModel.selectedPizzas.subscribe { pizzas ->
            homeRecyclerViewAdapter.setPizzaList(pizzas)
        }.let(compositeDisposable::add)
    }



    override fun onStop() {
        homeViewModel.setSearchText(binding.searchView.text.toString())
        super.onStop()
    }

    private fun showDetailsDialog(pizza: Pizza) {
        DetailsDialogFragment
            .newInstance(pizzaId = pizza.id)
            .show(
                requireActivity().supportFragmentManager,
                DetailsDialogFragment.TAG)
    }

    private fun FragmentHomeBinding.showMenuContainer() {
        menuContainer.visibility = View.VISIBLE
        searchContainer.visibility = View.INVISIBLE
        searchView.isEnabled = false
        searchView.setText("")
        keyboardManager.hideKeyboard()
    }


    private fun FragmentHomeBinding.showSearchContainer() {
        menuContainer.visibility = View.INVISIBLE
        searchContainer.visibility = View.VISIBLE
        searchView.isEnabled = true
        keyboardManager.showKeyboardAndSetFocus(focusEditText = searchView)
    }

    override fun customBackPressedHandlerFunction() {
        requireActivity().apply {
                if (homeViewModel.getAppBarConfig() == HomeAppBarConfig.SEARCH_MODE) {
                    homeViewModel.stopSearchMode()
                } else {
                    supportFragmentManager.popBackStack()
                    supportFragmentManager.popBackStack()
                    (this as MainActivity).parentBackPressedFunction()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "HomeFragment"
        @JvmStatic fun newInstance() = HomeFragment()
    }
}

