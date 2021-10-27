package com.example.ipizzaapp.di

import com.example.ipizzaapp.similar_db.PizzaDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    PicassoModule::class,
    AppContextModule::class,
    RetrofitModule::class,
    PicassoModule::class,
])
class AppModule {

}