package com.example.ipizzaapp.ui.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.ipizzaapp.network.IPizzaService
import com.example.ipizzaapp.pojo.Pizza
import com.example.ipizzaapp.similar_db.PizzaDatabase
import com.example.ipizzaapp.similar_db.getTestPizzas
import com.example.ipizzaapp.similar_db.oldPizzaToNew
import com.google.android.material.appbar.AppBarLayout
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.w3c.dom.ls.LSException

class HomeViewModel
    constructor(private val pizzaService: IPizzaService)
    : ViewModel() {


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
        setupPizzasFromApi()
        searchText.subscribe(this::filterPizzaByName).let(compositeDisposable::add)
    }

    private fun setupPizzasFromApi () {
        pizzaService.getPizzaList()
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
        super.onCleared()
        compositeDisposable.clear()
    }
}

enum class HomeAppBarConfig {
    SEARCH_MODE, BASE_MODE
}