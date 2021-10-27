package com.example.ipizzaapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class AppContextModule(private val _appContext: Context) {
    @Provides @Singleton @AppContext
    fun provideAppContext() : Context
        = _appContext
}

@Qualifier annotation class AppContext