package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "pizza_table")
data class LocalPizza(
	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("imageUrls")
	val imageUrls: List<String>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	@PrimaryKey(autoGenerate = false)
	val id: Int,
)