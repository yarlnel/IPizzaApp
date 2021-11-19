package com.example.data.repos

import android.util.Log
import com.example.data.converters.LocalPizzaConverter
import com.example.data.db.dao.PizzaDao
import com.example.data.network.retrofit.IPizzaApi
import com.example.domain.models.Pizza
import com.example.domain.repo.PizzaRepo
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class PizzaRepoImpl
    @Inject constructor(
        private val pizzaDao: PizzaDao,
        private val pizzaApi: IPizzaApi,
        private val localPizzaConverter: LocalPizzaConverter,
    ): PizzaRepo {

        override fun getAll(): Observable<List<Pizza>> {
            val observable = Observable.create<List<Pizza>> { emitter ->
                if (pizzaDao.countOfPizza() != 0)
                    pizzaDao.getAllPizza().subscribe {
                        localPizzas ->
                        val domainPizzas = localPizzas.map(localPizzaConverter::fromLocal)
                        emitter.onNext(domainPizzas)
                    }.let(emitter::setDisposable)

                pizzaApi.getPizzaList().subscribe (
                    {
                        localPizzas ->
                        val domainPizzas = localPizzas.map(localPizzaConverter::fromLocal)
                        emitter.onNext(domainPizzas)
                        pizzaDao.insertAll(localPizzas)
                    },
                    {
                        err -> Log.e(javaClass.simpleName, """
                            PizzaRepoImpl::getAll
                            Stacktrace:
                            ${err.stackTraceToString()}
                        """.trimIndent())
                    }
                ).let(emitter::setDisposable)
            }

            return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

        override fun getById(id: Int): Single<Pizza>
        = (
                if (pizzaDao.countPizzaById(id=id) > 0)
                    pizzaDao.getPizzaById(id=id)
                else
                    pizzaApi.getPizzaById(id=id)
          )
            .map(localPizzaConverter::fromLocal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


    override fun setupPizzaFromNetwork() : Disposable {
        val disposable = pizzaApi.getPizzaList()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe (
                {
                localPizzas ->
                    pizzaDao.insertAll(localPizzas)
                },
                {
                 err ->
                 Log.e(javaClass.simpleName,
                     """
                     |ErrIn setupPizzaFromNetwork():
                     |Stacktrace:
                     |${err.stackTraceToString()}
                     """.trimMargin()
                 )
                })

        return disposable
    }
}