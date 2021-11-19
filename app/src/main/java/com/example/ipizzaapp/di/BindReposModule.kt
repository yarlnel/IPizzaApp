package com.example.ipizzaapp.di

import com.example.data.repos.OrderRepoImpl
import com.example.data.repos.PizzaRepoImpl
import com.example.domain.repo.OrderRepo
import com.example.domain.repo.PizzaRepo
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface BindReposModule {
    @[Binds Singleton]
    fun bindPizzaRepo (pizzaRepoImpl: PizzaRepoImpl) : PizzaRepo

    @[Binds Singleton]
    fun bindOrderRepo (orderRepoImpl: OrderRepoImpl) : OrderRepo
}