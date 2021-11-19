package com.example.domain.usecase.order

import com.example.domain.repo.OrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddOnePizzaToOrder
@Inject constructor(
    private val orderRepo: OrderRepo
){
    operator fun invoke (id: Int) {
        orderRepo.addOnePizzaById(id = id)
    }
}