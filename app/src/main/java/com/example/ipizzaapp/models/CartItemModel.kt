package com.example.ipizzaapp.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CartItemModel  (
    val price: Int,
    val imageUrls: List<String>,
    val name: String,
    val description: String,
    val id: Int,
    val quantity: Int
)