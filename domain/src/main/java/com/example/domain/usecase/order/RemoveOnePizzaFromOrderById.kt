package com.example.domain.usecase.order

import com.example.domain.repo.OrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveOnePizzaFromOrderById
@Inject constructor(
    private val orderRepo: OrderRepo
){
    operator fun invoke (id: Int) {
        orderRepo.removeOnePizzaById(id = id)
    }
}