package com.example.ipizzaapp.pojo

import com.google.gson.annotations.SerializedName

data class Order(
	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("pizzaId")
	val pizzaId: Int? = null
)
