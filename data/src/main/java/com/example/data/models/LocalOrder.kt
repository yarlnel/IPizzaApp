package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "order_table")
data class LocalOrder(
	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("pizzaId")
	@PrimaryKey(autoGenerate = false)
	val pizzaId: Int? = null
)
