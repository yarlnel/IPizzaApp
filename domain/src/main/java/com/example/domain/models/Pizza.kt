package com.example.domain.models



data class Pizza(
	val price: Int,
	val imageUrls: List<String>,
	val name: String,
	val description: String,
	val id: Int,
)