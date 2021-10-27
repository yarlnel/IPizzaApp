package com.example.ipizzaapp.pojo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/*
data class Response(

	@field:SerializedName("Response")
	val response: List<ResponseItem?>? = null
)*/

@Parcelize
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
	val id: Int,
) : Parcelable