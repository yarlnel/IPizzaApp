package com.example.ipizzaapp.di

import android.content.Context
import com.example.ipizzaapp.MainApp
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
class ContextModule {
    @Provides @AppContext
    fun provideAppContext (mainApp: MainApp) : Context
        = mainApp.applicationContext

}

@Qualifier annotation class AppContext