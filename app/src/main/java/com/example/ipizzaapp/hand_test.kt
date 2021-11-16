package com.example.ipizzaapp

import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.retrofit.IPizzaApi
import io.reactivex.Observable
import io.reactivex.Observable.*

import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule


val retrofit = Retrofit.Builder()
    .baseUrl("https://springboot-kotlin-demo.herokuapp.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(IPizzaApi::class.java)

val compositeDisposable = CompositeDisposable()
fun main() {
    Observable.create<Int> { emitter ->
        repeat(10) {
            emitter.onNext(it)
        }
    }
        .delay(10, TimeUnit.SECONDS)
        .subscribe {
            println("$it) ${it * 12}")
        }

    Timer().schedule(30000) {
        print("thats alll")
    }
}