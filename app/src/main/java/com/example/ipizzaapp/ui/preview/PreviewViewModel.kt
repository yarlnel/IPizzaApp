package com.example.ipizzaapp.ui.preview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ipizzaapp.db.dao.OrderDao
import com.example.ipizzaapp.models.Order
import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.castom_providers.PizzaLoader
import com.example.ipizzaapp.network.retrofit.IPizzaApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class PreviewViewModel
    @Inject constructor(
        private val pizzaLoader: PizzaLoader,
        private val orderDao: OrderDao,
    ) : ViewModel() {
    private val _currentPageNumber
        = BehaviorSubject.create<Int>().apply {
            onNext(1)
        }

    private fun logException (throwable: Throwable) {
        Log.e(PreviewViewModel::class.java.simpleName, throwable.stackTraceToString())
    }


    val currentPageNumber : Observable<Int> = _currentPageNumber

    private val _selectedPizza = BehaviorSubject.create<Pizza>()

    val selectedPizza : Observable<Pizza> = _selectedPizza

    fun getSelectedPizzaOrNull () = _selectedPizza.value

    fun setCurrentPageNumber (pageNumber: Int) {
        _currentPageNumber.onNext(pageNumber)
    }

    fun addSelectedPizzaToCart () {
        _selectedPizza.value?.id?.let { pizzaId ->
            orderDao.insertOrder(Order(quantity = 1, pizzaId = pizzaId))
        }
    }

    fun setupPizzaById(pizzaId: Int) {
        pizzaLoader.loadPizzaById(
            id = pizzaId,
            onPizzaLoaded = _selectedPizza::onNext
        )
    }

    override fun onCleared() {
        pizzaLoader.clearDisposable()
        super.onCleared()
    }
}