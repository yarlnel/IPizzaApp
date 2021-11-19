package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.db.converters.BitmapToStringConverter
import com.example.data.db.converters.ListToJsonConverter
import com.example.data.db.dao.ImageDao
import com.example.data.db.dao.OrderDao
import com.example.data.db.dao.PizzaDao
import com.example.data.models.LocalImageEntity
import com.example.data.models.LocalOrder
import com.example.data.models.LocalPizza

@Database(
    entities = [
        LocalOrder::class,
        LocalPizza::class,
        LocalImageEntity::class,
    ],
    version = 3,
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