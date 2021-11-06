package com.example.ipizzaapp.di

import android.content.Context
import androidx.room.Room
import com.example.ipizzaapp.db.MainDatabase
import com.example.ipizzaapp.db.dao.ImageDao
import com.example.ipizzaapp.db.dao.OrderDao
import com.example.ipizzaapp.db.dao.PizzaDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides @Singleton
    fun provideMainDatabase (@AppContext appContext: Context) : MainDatabase
        = Room.databaseBuilder(
            appContext, MainDatabase::class.java, "main_database")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides @Singleton
    fun provideOrderDao (mainDatabase: MainDatabase) : OrderDao
        = mainDatabase.orderDao()

    @Provides @Singleton
    fun providePizzaDao (mainDatabase: MainDatabase) : PizzaDao
        = mainDatabase.pizzaDao()

    @Provides @Singleton
    fun provideImageDao(mainDatabase: MainDatabase) : ImageDao
        = mainDatabase.imageDao()
}
