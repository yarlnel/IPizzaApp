package com.example.ipizzaapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/*
data class Response(

	@field:SerializedName("Response")
	val response: List<ResponseItem?>? = null
)*/

@Entity(tableName = "pizza_table")
data class Pizza(
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