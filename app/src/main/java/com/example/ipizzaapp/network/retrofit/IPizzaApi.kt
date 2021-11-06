package com.example.ipizzaapp.network.retrofit

import com.example.ipizzaapp.models.Order
import com.example.ipizzaapp.models.Pizza
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IPizzaApi {
    @GET("/pizza")
    fun getPizzaList () : Single<List<Pizza>>

    @GET("/pizza/{id}")
    fun getPizzaById(@Path("id") id: Int) : Single<Pizza>

    @POST("/pizza/order")
    fun placeOrders(@Body orders: List<Order>)
}