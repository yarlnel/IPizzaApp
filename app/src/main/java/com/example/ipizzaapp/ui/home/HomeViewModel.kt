package com.example.ipizzaapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ipizzaapp.similar_db.Pizza
import com.example.ipizzaapp.similar_db.PizzaDao

class HomeViewModel(private val pizzaDao: PizzaDao) : ViewModel() {
    private val _pizzaList : MutableLiveData<List<Pizza>>
        = MutableLiveData(pizzaDao.getAll())

    val pizzaList : LiveData<List<Pizza>> = _pizzaList

    private val _appBarConfig : MutableLiveData<HomeAppBarConfig>
        = MutableLiveData(HomeAppBarConfig.BASE_MODE)

    val appBarConfig : LiveData<HomeAppBarConfig> = _appBarConfig


    fun filterPizzaByName(name: String) {
        pizzaDao
            .getAll()
            .filter { name.toLowerCase() in it.name.toLowerCase() }
            .let(_pizzaList::postValue)
    }

    fun startSearchMode() {
        _appBarConfig.value = HomeAppBarConfig.SEARCH_MODE
    }

    fun stopSearchMode() {
        _appBarConfig.value = HomeAppBarConfig.BASE_MODE
    }
}

enum class HomeAppBarConfig {
    SEARCH_MODE, BASE_MODE
}