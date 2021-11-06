package com.example.ipizzaapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ipizzaapp.db.dao.PizzaDao
import com.example.ipizzaapp.network.retrofit.IPizzaApi
import com.example.ipizzaapp.models.Pizza
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class HomeViewModel
    @Inject constructor(
        private val pizzaApi: IPizzaApi,
        private val pizzaDao: PizzaDao,
    ) : ViewModel() {

    private fun log ( th: Throwable) {
        Log.e(HomeViewModel::class.java.simpleName, th.stackTraceToString())
    }

    private val compositeDisposable = CompositeDisposable()

    private val _searchText =
        BehaviorSubject.create<String>()
    private val searchText : Observable<String> = _searchText

    fun getSearchText() = _searchText.value ?: ""

    fun setSearchText(text: String) {
        _searchText.onNext(text)
    }

    private val _selectedPizzas = BehaviorSubject.create<List<Pizza>>()
    val selectedPizzas : Observable<List<Pizza>> = _selectedPizzas
    private val baseListOfPizza = mutableListOf<Pizza>()

    init {
        searchText
            .subscribe(this::filterPizzaByName)
            .let(compositeDisposable::add)
    }

    fun setupPizza () {
        if (baseListOfPizza.count() == 0) {
            setupPizzasFromDb()
        } else {
            setupSavedPizza()
        }
    }

    private fun setupSavedPizza () {
        _selectedPizzas.onNext(baseListOfPizza)
        if (!_searchText.value.isNullOrEmpty()) {
            filterPizzaByName(_searchText.value.toString())
            _appBarConfig.onNext(HomeAppBarConfig.SEARCH_MODE)
        }
    }


    private fun setupPizzasFromDb () {
        pizzaDao
            .getAllPizza()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listOfPizza ->
                if (baseListOfPizza.count() == 0) {
                    baseListOfPizza.clear()
                    baseListOfPizza.addAll(listOfPizza)
                    _selectedPizzas.onNext(listOfPizza)
                }
                else _selectedPizzas.onNext(baseListOfPizza)
            }, ::log)
            .let(compositeDisposable::add)
    }


    private val _appBarConfig =
        BehaviorSubject.create<HomeAppBarConfig>().apply {
            onNext(HomeAppBarConfig.BASE_MODE)
        }

    val appBarConfig : Observable<HomeAppBarConfig> = _appBarConfig

    fun getAppBarConfig () = _appBarConfig.value


    private fun filterPizzaByName(name: String) {
        baseListOfPizza
            .filter { name.lowercase() in it.name.lowercase() }
            .let(_selectedPizzas::onNext)
    }

    fun startSearchMode() {
        _appBarConfig.onNext(HomeAppBarConfig.SEARCH_MODE)
    }

    fun stopSearchMode() {
        _appBarConfig.onNext(HomeAppBarConfig.BASE_MODE)
        _selectedPizzas.onNext(baseListOfPizza)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

enum class HomeAppBarConfig {
    SEARCH_MODE, BASE_MODE
}