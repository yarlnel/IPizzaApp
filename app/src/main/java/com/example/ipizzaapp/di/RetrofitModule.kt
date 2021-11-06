package com.example.ipizzaapp.di

import com.example.ipizzaapp.network.retrofit.IPizzaApi
import dagger.Module
import dagger.Provides
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides @Singleton
    fun provideRxCallAdapterFactory() : CallAdapter.Factory
        = RxJava2CallAdapterFactory.create()
    @Provides @Singleton
    fun provideGsonConvertorFactory() : Converter.Factory
         = GsonConverterFactory.create()

    @IPizzaApiRetrofit @Provides @Singleton
    fun provideIPizzaRetrofit (
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ) : Retrofit
        = Retrofit.Builder()
            .baseUrl("https://springboot-kotlin-demo.herokuapp.com/")
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()

    @Provides @Singleton
    fun provideIPizzaService (
        @IPizzaApiRetrofit retrofit: Retrofit
    ) : IPizzaApi
        = retrofit.create(IPizzaApi::class.java)

}

@Qualifier annotation class IPizzaApiRetrofit

