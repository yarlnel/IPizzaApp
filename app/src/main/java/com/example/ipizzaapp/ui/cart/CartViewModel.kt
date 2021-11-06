package com.example.ipizzaapp.ui.cart

import androidx.lifecycle.ViewModel

import com.example.ipizzaapp.db.dao.OrderDao
import com.example.ipizzaapp.models.CartItemModel
import com.example.ipizzaapp.models.Order
import com.example.ipizzaapp.network.retrofit.IPizzaApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class CartViewModel
    @Inject constructor(
        private val orderDao: OrderDao,
        private val pizzaApi: IPizzaApi,
    ) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _orderList = BehaviorSubject.create<List<Order>>()
    val orderList : Observable<List<Order>> = _orderList

    fun quantityById(id: Int): Observable<Int> = 
        orderDao
        .getQuantityById(id)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
    

    init {
        orderDao.getOrdersList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { orders ->
                _orderList.onNext(orders.filter { it.quantity ?: 0 >= 1 })
            }
            .let(compositeDisposable::add)
    }

    fun addOnePizza(id: Int) {
        orderDao.addOnePizzaToOrderById(id)
    }

    fun removeOnePizza(id: Int) {
        orderDao.removeOnePizzaFromOrderById(id)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun clearOrders() {
        orderDao.deleteAll()
    }
}