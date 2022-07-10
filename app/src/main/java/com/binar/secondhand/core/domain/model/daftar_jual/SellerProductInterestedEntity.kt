package com.binar.secondhand.core.domain.model.daftar_jual

import com.binar.secondhand.core.data.remote.daftar_jual.response.SellerProductInterestedItem
import com.google.gson.annotations.SerializedName

data class SellerProductInterestedEntity(
    val id: Int?,
    val buyerId: Int?,
    val productId: Int,
    val imageProduct: String?,
    val productName: String?,
    val transactionDate: String?,
    val price: Int?,
    val basePrice: String?,
    val user: UserBuyer?

){
    data class UserBuyer(
        val address: String?,
        val city: String?,
        val email: String?,
        val fullName: String?,
        val id: Int?,
        val phoneNumber: String?
    )
}