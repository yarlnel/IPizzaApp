package com.example.ipizzaapp.di

import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class LoggingModule {
    @Singleton @Provides @AppIsInLoggingMode
    fun provideAppInLoggingModeFlag() = true
}

@Qualifier annotation class AppIsInLoggingMode