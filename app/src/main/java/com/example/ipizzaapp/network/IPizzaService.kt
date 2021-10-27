package com.example.ipizzaapp.network

import com.example.ipizzaapp.pojo.Order
import com.example.ipizzaapp.pojo.Pizza
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IPizzaService {
    @GET("/pizza")
    fun getPizzaList () : Single<List<Pizza>>

    @GET("/pizza/{id}")
    fun getPizzaById(@Path("id") id: Int) : Single<Pizza>

    @POST("/pizza/order")
    fun placeOrders(@Body orders: List<Order>)
}