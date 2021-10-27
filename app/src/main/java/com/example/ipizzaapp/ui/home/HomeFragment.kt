package com.example.ipizzaapp.ui.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.ipizzaapp.R
import com.example.ipizzaapp.abstractions.BackPressedStrategyOwner
import com.example.ipizzaapp.appComponent
import com.example.ipizzaapp.databinding.FragmentHomeBinding
import com.example.ipizzaapp.pojo.Pizza
import com.example.ipizzaapp.ui.MainActivity
import com.example.ipizzaapp.ui.details.DetailsDialogFragment
import com.example.ipizzaapp.ui.preview.PreviewFragment
import com.example.ipizzaapp.utils.custom_managers.CustomSoftKeyboardManager
import com.example.ipizzaapp.utils.listeners.OnEnterKeyPressed
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class HomeFragment : Fragment(R.layout.fragment_home), BackPressedStrategyOwner {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val router by lazy {
        (requireActivity() as MainActivity)
            .router.from(this@HomeFragment)
    }

    private val homeViewModel : HomeViewModel by viewModels {
        HomeViewModelFactory(appComponent.iPizzaApi())
    }

    @Inject lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter

    // Если использовать екстеншены вместо класса CustomSoftKeyboardManager
    // То нам прейдется чаще вызывать метод
    // activity.getSystemService(Activity.INPUT_METHOD_SERVICE)
    // для получения InputManager, что не очень хорошо
    //
    // поэтому я решил оставить класс CustomSoftKeyboardManager
    private val keyboardManager by lazy {
        CustomSoftKeyboardManager(requireActivity() as AppCompatActivity)
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeRecyclerViewAdapter.onItemBind { (binding, pizza) ->
            with(binding) {
                root.setOnClickListener {
                    showDetailsDialog(pizza = pizza)
                    homeViewModel.stopSearchMode()
                }
                itemImageView.setOnClickListener {
                    router.goTo(
                        PreviewFragment.TAG,
                        PreviewFragment.newInstance(pizza = pizza))
                    homeViewModel.stopSearchMode()
                }
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

        homeViewModel.selectedPizzas.subscribe{ pizzas ->
            homeRecyclerViewAdapter.setPizzaList(pizzas)
        }.let(compositeDisposable::add)
    }

    override fun onStop() {
        homeViewModel.setSearchText(binding.searchView.text.toString())
        super.onStop()
    }

    private fun showDetailsDialog(pizza: Pizza) {
        DetailsDialogFragment
            .newInstance(pizza = pizza)
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

