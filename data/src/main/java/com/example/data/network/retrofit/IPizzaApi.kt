package com.example.data.network.retrofit

import com.example.data.models.LocalOrder
import com.example.data.models.LocalPizza
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IPizzaApi {
    @GET("/pizza")
    fun getPizzaList () : Single<List<LocalPizza>>

    @GET("/pizza/{id}")
    fun getPizzaById(@Path("id") id: Int) : Single<LocalPizza>

    @POST("/pizza/order")
    fun placeOrders(@Body orders: List<LocalOrder>) : Completable
}