package com.example.domain.usecase.order

import com.example.domain.repo.OrderRepo
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetOrderQuantityById
@Inject constructor(
    private val orderRepo: OrderRepo
){
    operator fun invoke (id: Int) : Observable<Int> {
        return orderRepo.getQuantityById(id = id)
    }
}