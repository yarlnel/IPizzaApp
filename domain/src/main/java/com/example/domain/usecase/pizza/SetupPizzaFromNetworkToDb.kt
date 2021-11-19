package com.example.domain.usecase.pizza

import com.example.domain.repo.PizzaRepo
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetupPizzaFromNetworkToDb
@Inject constructor(
    private val pizzaRepo: PizzaRepo
){
    /**
     *  Не забудь обработать `Disposable`
     */
    operator fun invoke () : Disposable {
        return pizzaRepo.setupPizzaFromNetwork()
    }
}