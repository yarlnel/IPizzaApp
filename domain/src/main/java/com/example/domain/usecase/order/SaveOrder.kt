package com.example.domain.usecase.order

import com.example.domain.models.Order
import com.example.domain.repo.OrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveOrder
@Inject constructor(
    private val orderRepo: OrderRepo
){
    operator fun invoke (order: Order) {
        orderRepo.save(order = order)
    }
}