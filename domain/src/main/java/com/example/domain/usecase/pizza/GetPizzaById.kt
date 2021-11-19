package com.example.domain.usecase.pizza

import com.example.domain.models.Pizza
import com.example.domain.repo.PizzaRepo
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPizzaById
@Inject constructor(
    private val pizzaRepo: PizzaRepo
){
    operator fun invoke (id: Int) : Single<Pizza> {
        return pizzaRepo.getById(id=id)
    }
}