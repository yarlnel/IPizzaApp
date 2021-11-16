package com.example.ipizzaapp.utils.custom_managers.setup

import android.graphics.BitmapFactory
import android.os.AsyncTask.execute
import android.util.Log
import com.example.ipizzaapp.abstractions.BaseRxAndLoggingClass
import com.example.ipizzaapp.db.dao.ImageDao
import com.example.ipizzaapp.db.dao.PizzaDao
import com.example.ipizzaapp.models.ImageEntity
import com.example.ipizzaapp.models.Pizza
import com.example.ipizzaapp.network.retrofit.IPizzaApi
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 *  Класс `SetupDataManager` отвечает за асинхронную поставку даных в БД
 *  На начальном этапе загрузки приложения.
 *  Желательно ислпользовать в `MainActivity` или `Application` классах
 */
class SetupDataManager
    @Inject constructor(
        private val pizzaDao: PizzaDao,
        private val imageDao: ImageDao,
        private val pizzaApi: IPizzaApi,
    ): BaseRxAndLoggingClass() {

    fun setupAllToDb () {
        /*
        pizzaApi
            .getPizzaList()
            .delay(10, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe({ listOfPizza ->
                setupImages(listOfPizza)
                setupPizza(listOfPizza)
            }, ::log).let(compositeDisposable::add)
*/

    }

    private fun setupImages (pizzas: List<Pizza>) {
        try {
            pizzas.flatMap(Pizza::imageUrls).forEach { imageUrl ->
                if (imageDao.countImagesByUrl(imageUrl) == 0) {
                    val bitmapInputStream =
                        URL(imageUrl).openConnection().getInputStream()

                    val bitmap = BitmapFactory.decodeStream(bitmapInputStream)

                    imageDao.insertImage(ImageEntity(imageUrl, bitmap))
                }
            }

        } catch (e: IOException) {
            Log.e(SetupDataManager::class.java.simpleName,
                "Network exception:\n\n${e.stackTraceToString()}")
        }
    }

    private fun setupPizza (pizzas: List<Pizza>) {
        pizzaDao.insertAll(pizzas)
    }
}