package com.example.ipizzaapp

import com.example.ipizzaapp.network.IPizzaService
import com.example.ipizzaapp.similar_db.pizzaSet
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val retrofit = Retrofit.Builder()
    .baseUrl("https://springboot-kotlin-demo.herokuapp.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(IPizzaService::class.java)

val compositeDisposable = CompositeDisposable()
fun main() {
    api.getPizzaList().subscribe({ pizzaList ->
        println(pizzaList.flatMap { it.imageUrls!! }.joinToString("\n"))
    }, {
        println(it.stackTraceToString())
    }).dispose()
}