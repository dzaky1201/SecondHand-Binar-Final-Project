package com.binar.secondhand.core.domain.model.detail


data class Order (
    val basePrice: Int = 0,
    val buyerId: Int = 0,
    val createdAt: String = "",
    val id: Int = 0,
    val imageProduct: String = "",
    val price: Int = 0,
    val productId: Int = 0,
    val productName: String = "",
    val status: String = "",
    val transactionDate: String = "",
    val updatedAt: String = ""
)