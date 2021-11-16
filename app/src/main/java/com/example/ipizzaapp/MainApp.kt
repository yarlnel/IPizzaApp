package com.example.ipizzaapp


import com.example.ipizzaapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MainApp : DaggerApplication() {
    override fun applicationInjector()
        : AndroidInjector<out DaggerApplication>
        = DaggerAppComponent.builder()
            .bindContext(this)
            .build()
}
