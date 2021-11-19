package com.example.ipizzaapp.ui.cart

import androidx.lifecycle.ViewModel

import com.example.domain.models.Order
import com.example.domain.models.Pizza
import com.example.domain.usecase.order.*
import com.example.domain.usecase.pizza.GetAllPizza
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CartViewModel
    @Inject constructor(
        private val getOrderQuantityById: GetOrderQuantityById,
        private val getAllOrders: GetAllOrders,
        private val getAllPizza: GetAllPizza,
        private val addOnePizzaToOrder: AddOnePizzaToOrder,
        private val removeOnePizzaFromOrderById: RemoveOnePizzaFromOrderById,
        private val deleteAllOrders: DeleteAllOrders,
        private val placeOrderUseCase: PlaceOrder,
    ) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _orderList = BehaviorSubject.create<List<Order>>()
    val orderList : Observable<List<Order>> = _orderList


    fun quantityById(id: Int): Observable<Int> = 
        getOrderQuantityById(id=id)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
    

    init {
        getAllOrders()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { orders ->
                _orderList.onNext(orders.filter { it.quantity ?: 0 >= 1 })
            }
            .let(compositeDisposable::add)
    }

    fun addOnePizza(id: Int) {
        addOnePizzaToOrder(id = id)
    }

    fun removeOnePizza(id: Int) {
        removeOnePizzaFromOrderById(id=id)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun placeOrder (onError: (Throwable) -> Unit, onSuccess: () -> Unit) {
        val orders  = _orderList.value ?: throw Exception("""
            ${javaClass.simpleName} placeOrder()
            Err -> order mustn't be null
        """.trimIndent())

        placeOrderUseCase (orders)
            .subscribe(onSuccess, onError)
            .let(compositeDisposable::add)
    }


    fun clearOrders() {
        deleteAllOrders()
    }
}