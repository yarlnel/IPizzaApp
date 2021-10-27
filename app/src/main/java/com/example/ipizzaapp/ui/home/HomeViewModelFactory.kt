package com.example.ipizzaapp.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ipizzaapp.appComponent
import com.example.ipizzaapp.di.AppComponent
import com.example.ipizzaapp.network.IPizzaService
import com.example.ipizzaapp.similar_db.PizzaDao

class HomeViewModelFactory
    (private val pizzaService: IPizzaService)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(pizzaService = pizzaService) as T
    }
}
