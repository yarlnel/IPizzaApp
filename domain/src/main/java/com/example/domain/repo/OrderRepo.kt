package com.example.domain.repo

import com.example.domain.models.Order
import io.reactivex.Completable
import io.reactivex.Observable

interface OrderRepo {
    fun send(orders: List<Order>) : Completable
    fun getAll() : Observable<List<Order>>
    fun getQuantityById(id: Int) : Observable<Int>
    fun addOnePizzaById(id: Int)
    fun removeOnePizzaById(id: Int)
    fun deleteAll()
    fun save(order: Order)
}