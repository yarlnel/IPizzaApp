package com.example.domain.repo

import com.example.domain.models.Pizza
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface PizzaRepo {
    fun getAll() : Observable<List<Pizza>>
    fun getById(id: Int) : Single<Pizza>
    fun setupPizzaFromNetwork () : Disposable
}
