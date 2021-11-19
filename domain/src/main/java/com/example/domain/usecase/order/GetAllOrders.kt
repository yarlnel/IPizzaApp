package com.example.domain.usecase.order

import com.example.domain.models.Order
import com.example.domain.repo.OrderRepo
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllOrders
@Inject constructor(
    private val orderRepo: OrderRepo
){
    operator fun invoke (): Observable<List<Order>> {
        return orderRepo.getAll()
    }
}