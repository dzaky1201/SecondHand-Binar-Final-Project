package com.binar.secondhand.core.domain.model.home

import com.binar.secondhand.core.data.BaseEquatable

data class Product(
    val basePrice: Int = 0,
    val categories: List<Category> = listOf(Category()),
    val createdAt: String = "",
    val description: String = "",
    val id: Int = 0,
    val imageName: String = "",
    val imageUrl: String = "",
    val location: String = "",
    val name: String = "",
    val status: String = "",
    val updatedAt: String = "",
    val userId: Int = 0
): BaseEquatable(name) {
    data class Category(
        val id: Int = 0,
        val name: String = ""
    )
}
