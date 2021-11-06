package com.example.ipizzaapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ipizzaapp.db.converters.BitmapToStringConverter
import com.example.ipizzaapp.db.converters.ListToJsonConverter
import com.example.ipizzaapp.db.dao.ImageDao
import com.example.ipizzaapp.db.dao.OrderDao
import com.example.ipizzaapp.db.dao.PizzaDao
import com.example.ipizzaapp.models.ImageEntity
import com.example.ipizzaapp.models.Order
import com.example.ipizzaapp.models.Pizza

@Database(
    entities = [
        Order::class,
        Pizza::class,
        ImageEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
@TypeConverters(
    ListToJsonConverter::class,
    BitmapToStringConverter::class,
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun orderDao () : OrderDao
    abstract fun pizzaDao () : PizzaDao
    abstract fun imageDao () : ImageDao
}