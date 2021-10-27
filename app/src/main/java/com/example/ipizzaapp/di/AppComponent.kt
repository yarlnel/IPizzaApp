package com.example.ipizzaapp.di

import com.example.ipizzaapp.network.IPizzaService
import com.example.ipizzaapp.ui.home.HomeFragment
import com.example.ipizzaapp.ui.home.HomeViewModel
import com.example.ipizzaapp.ui.preview.PreviewFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(previewFragment: PreviewFragment)
    fun iPizzaApi() : IPizzaService
}