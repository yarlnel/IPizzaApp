package com.example.domain.usecase.pizza

import com.example.domain.models.Pizza
import com.example.domain.repo.PizzaRepo
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllPizza
@Inject constructor(
    private val pizzaRepo: PizzaRepo
) {
    operator fun invoke () : Observable<List<Pizza>> {
        return pizzaRepo.getAll()
    }
}