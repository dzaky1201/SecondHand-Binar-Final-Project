package com.binar.secondhand.core.domain.model.daftar_jual

import com.google.gson.annotations.SerializedName

data class UpdateStatusProduct(
    val basePrice: String?,
    val buyerId: Int?,
    val createdAt: String?,
    val id: Int?,
    val imageProduct: String?,
    val price: Int?,
    val productId: Int?,
    val productName: String?,
    val status: String?,
    val transactionDate: String?,
    val updatedAt: String?
)
