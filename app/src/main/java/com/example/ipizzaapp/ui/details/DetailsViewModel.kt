package com.example.ipizzaapp.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ipizzaapp.db.dao.OrderDao
import com.example.ipizzaapp.models.Order
import com.example.ipizzaapp.network.retrofit.IPizzaApi
import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.castom_providers.PizzaLoader
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class DetailsViewModel
    @Inject constructor(
        private val pizzaLoader: PizzaLoader,
        private val orderDao: OrderDao,
    ) : ViewModel() {


    private val _selectedPizza = BehaviorSubject.create<Pizza>()

    val selectedPizza: Observable<Pizza> = _selectedPizza

    private fun logException(throwable: Throwable) {
        Log.e(DetailsViewModel::class.java.simpleName, throwable.stackTraceToString())
    }

    fun getSelectedPizzaOrNull () = _selectedPizza.value

    fun setupPizzaById(id: Int) {
        pizzaLoader.loadPizzaById(
            id = id,
            onPizzaLoaded = _selectedPizza::onNext
        )
    }

    fun addSelectedPizzaToCart() {
        _selectedPizza.value?.id?.let { pizzaId ->
            orderDao.insertOrder(Order(quantity = 1, pizzaId = pizzaId))
        }
    }

    override fun onCleared() {
        pizzaLoader.clearDisposable()
        super.onCleared()
    }
}