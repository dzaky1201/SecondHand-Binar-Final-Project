package com.binar.secondhand.core.domain.model.jual

import com.google.gson.annotations.SerializedName

data class SellerProduct(
    val basePrice: Int?,
    val createdAt: String?,
    val description: String?,
    val id: Int?,
    val imageName: String?,
    val imageUrl: String?,
    val location: String?,
    val name: String?,
    val status: String?,
    val updatedAt: String?,
    val userId: Int?
)
