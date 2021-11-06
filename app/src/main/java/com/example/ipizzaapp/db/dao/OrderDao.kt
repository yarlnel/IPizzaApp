package com.example.ipizzaapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ipizzaapp.models.Order
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface OrderDao {
    @Query("SELECT * FROM order_table")
    fun getOrdersList () : Observable<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: Order)



    @Query("SELECT quantity FROM order_table WHERE pizzaId = :id")
    fun getQuantityById(id: Int) : Observable<Int>

    @Query("""
        UPDATE order_table
            SET quantity = (SELECT quantity WHERE pizzaId = :id) + 1
        WHERE pizzaId = :id
    """)
    fun addOnePizzaToOrderById(id: Int)

    @Query("""
        UPDATE order_table  
            SET quantity = 
                (SELECT quantity FROM order_table WHERE pizzaId = :id) - 1
        WHERE pizzaId = :id
    """)
    fun removeOnePizzaFromOrderById(id: Int)

    @Query("DELETE FROM order_table")
    fun deleteAll()


}