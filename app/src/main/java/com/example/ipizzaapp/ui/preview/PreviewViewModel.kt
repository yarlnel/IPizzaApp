package com.example.ipizzaapp.ui.preview

import android.util.Log
import androidx.lifecycle.ViewModel


import com.example.domain.models.Order

import com.example.domain.models.Pizza
import com.example.domain.usecase.order.SaveOrder
import com.example.domain.usecase.pizza.GetPizzaById

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class PreviewViewModel
    @Inject constructor(
        private val saveOrder: SaveOrder,
        private val getPizzaById: GetPizzaById,
    ) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
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
            saveOrder(Order(quantity = 1, pizzaId = pizzaId))
        }
    }

    fun setupPizzaById(pizzaId: Int) {
        getPizzaById(id = pizzaId)
            .subscribe(_selectedPizza::onNext, ::logException)
            .let(compositeDisposable::add)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}