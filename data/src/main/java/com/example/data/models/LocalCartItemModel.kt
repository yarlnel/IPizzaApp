package com.example.data.models

data class LocalCartItemModel  (
    val price: Int,
    val imageUrls: List<String>,
    val name: String,
    val description: String,
    val id: Int,
    val quantity: Int
)