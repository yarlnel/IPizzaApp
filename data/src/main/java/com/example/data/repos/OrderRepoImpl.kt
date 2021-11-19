package com.example.data.repos

import com.example.data.converters.LocalOrderConverter
import com.example.data.db.dao.OrderDao
import com.example.data.network.retrofit.IPizzaApi
import com.example.domain.models.Order
import com.example.domain.repo.OrderRepo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OrderRepoImpl
@Inject constructor(
       private val orderDao: OrderDao,
       private val pizzaApi: IPizzaApi,
       private val localOrderConverter: LocalOrderConverter,
) : OrderRepo {
    override fun send(orders: List<Order>) : Completable {
        val localOrders = orders.map(localOrderConverter::toLocal)
        return pizzaApi.placeOrders(localOrders)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun getAll(): Observable<List<Order>> {
        val ordersObservable = orderDao.getOrdersList().map {
            it.map(localOrderConverter::fromLocal)
        }
        return ordersObservable
    }

    override fun getQuantityById(id: Int): Observable<Int> {
        return orderDao.getQuantityById(id=id)
    }

    override fun addOnePizzaById(id: Int) {
        orderDao.addOnePizzaToOrderById(id = id)
    }

    override fun removeOnePizzaById(id: Int) {
        orderDao.removeOnePizzaFromOrderById(id = id)
    }

    override fun deleteAll() {
        orderDao.deleteAll()
    }

    override fun save(order: Order) {
        val localOrder = localOrderConverter.toLocal(domainModel = order)
        orderDao.insertOrder(localOrder)
    }
}