package com.example.domain.usecase.order

import com.example.domain.models.Order
import com.example.domain.repo.OrderRepo
import io.reactivex.Completable

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceOrder
@Inject constructor(
    private val orderRepo: OrderRepo
){
    operator fun invoke(orders: List<Order>) : Completable{
        return orderRepo.send(orders = orders)
    }
}