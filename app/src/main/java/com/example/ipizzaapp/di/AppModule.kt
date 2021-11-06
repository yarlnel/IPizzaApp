package com.example.ipizzaapp.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule


@Module(includes = [
    PicassoModule::class,
    AppContextModule::class,
    RetrofitModule::class,
    PicassoModule::class,
    ViewModelModule::class,
    FragmentModule::class,
    DatabaseModule::class,
    AndroidSupportInjectionModule::class,
])
class AppModule