package com.example.ipizzaapp.db.dao

import androidx.room.*
import com.example.ipizzaapp.models.Pizza
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PizzaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (pizza: Pizza)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll (pizzas: List<Pizza>)

    @Query("SELECT * FROM pizza_table WHERE id = :id LIMIT 1")
    fun getPizzaById(id: Int): Single<Pizza>

    @Query("SELECT * FROM pizza_table")
    fun getAllPizza () : Observable<List<Pizza>>

    @Query("SELECT COUNT(*) FROM pizza_table")
    fun countOfPizza () : Int

    @Query("SELECT COUNT(*) FROM pizza_table WHERE id = :id")
    fun countPizzaById (id: Int) : Int


    @Query("DELETE FROM pizza_table")
    fun deleteAll()
}