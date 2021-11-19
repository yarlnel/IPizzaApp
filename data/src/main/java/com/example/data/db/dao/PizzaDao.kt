package com.example.data.db.dao

import androidx.room.*
import com.example.data.models.LocalPizza
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PizzaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (pizza: LocalPizza)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll (pizzas: List<LocalPizza>)

    @Query("SELECT * FROM pizza_table WHERE id = :id LIMIT 1")
    fun getPizzaById(id: Int): Single<LocalPizza>

    @Query("SELECT * FROM pizza_table")
    fun getAllPizza () : Observable<List<LocalPizza>>

    @Query("SELECT COUNT(*) FROM pizza_table")
    fun countOfPizza () : Int

    @Query("SELECT COUNT(*) FROM pizza_table WHERE id = :id")
    fun countPizzaById (id: Int) : Int


    @Query("DELETE FROM pizza_table")
    fun deleteAll()
}