package com.example.ipizzaapp.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.models.Order
import com.example.domain.models.Pizza
import com.example.domain.usecase.order.SaveOrder
import com.example.domain.usecase.pizza.GetPizzaById
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class DetailsViewModel
    @Inject constructor(
        private val getPizzaById: GetPizzaById,
        private val saveOrder: SaveOrder,
    ) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    private val _selectedPizza = BehaviorSubject.create<Pizza>()

    val selectedPizza: Observable<Pizza> = _selectedPizza

    private fun logException(throwable: Throwable) {
        Log.e(DetailsViewModel::class.java.simpleName, throwable.stackTraceToString())
    }

    fun getSelectedPizzaOrNull () = _selectedPizza.value

    fun setupPizzaById(id: Int) {
        getPizzaById(id=id)
            .subscribe(_selectedPizza::onNext, )
            .let(compositeDisposable::add)

    }

    fun addSelectedPizzaToCart() {
        _selectedPizza.value?.id?.let { pizzaId ->
            saveOrder(Order(quantity = 1, pizzaId = pizzaId))
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}