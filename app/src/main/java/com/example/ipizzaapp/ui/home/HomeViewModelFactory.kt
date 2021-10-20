package com.example.ipizzaapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ipizzaapp.similar_db.Pizza
import com.example.ipizzaapp.similar_db.PizzaDao

class HomeViewModelFactory
    (private val pizzaDao: PizzaDao)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(pizzaDao) as T
    }

}