package com.example.ipizzaapp.network.castom_providers

import com.example.ipizzaapp.abstractions.BaseRxAndLoggingClass
import com.example.ipizzaapp.db.dao.PizzaDao
import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.retrofit.IPizzaApi
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PizzaLoader
@Inject constructor(
    private val pizzaDao: PizzaDao,
    private val pizzaApi: IPizzaApi,
) : BaseRxAndLoggingClass() {


    private var _subscribeOn = Schedulers.io()
    private var _observeOn = AndroidSchedulers.mainThread()

    fun subscribeOn (scheduler: Scheduler) {
        _subscribeOn = scheduler
    }

    fun observeOn (scheduler: Scheduler) {
        _observeOn = scheduler
    }


    fun loadPizzaById (id: Int, onPizzaLoaded: (Pizza) -> Unit) {
        if (pizzaDao.countPizzaById(id = id) == 0)
            pizzaApi.getPizzaById(id = id)
                .subscribeOn(_subscribeOn)
                .observeOn(_observeOn)
                .subscribe({ pizza ->
                    onPizzaLoaded(pizza)
                    pizzaDao.insert(pizza)
                }, ::log)
                .let(compositeDisposable::add)
        else
            pizzaDao.getPizzaById( id)
                .subscribeOn(_subscribeOn)
                .observeOn(_observeOn)
                .subscribe(onPizzaLoaded, ::log)
                .let(compositeDisposable::add)

    }

    fun loadAllPizzas (onListOfPizzaLoaded: (List<Pizza>) -> Unit) {
        pizzaDao.getAllPizza()
            .subscribeOn(_subscribeOn)
            .observeOn(_observeOn)
            .subscribe(onListOfPizzaLoaded, ::log)
            .let(compositeDisposable::add)

        pizzaApi.getPizzaList()
            .subscribeOn(_subscribeOn)
            .observeOn(_observeOn)
            .subscribe(onListOfPizzaLoaded, ::log)
            .let(compositeDisposable::add)
    }
}