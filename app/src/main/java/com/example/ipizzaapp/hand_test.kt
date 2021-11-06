package com.example.ipizzaapp

import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.retrofit.IPizzaApi
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("https://springboot-kotlin-demo.herokuapp.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(IPizzaApi::class.java)

val compositeDisposable = CompositeDisposable()
fun main() {
    api.getPizzaList().subscribe({ pizzas ->
        pizzas.flatMap(Pizza::imageUrls).forEach(::println)
    }, {}).let(compositeDisposable::add)
}