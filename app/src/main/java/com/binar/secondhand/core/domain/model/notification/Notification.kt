package com.binar.secondhand.core.domain.model.notification


import com.binar.secondhand.core.data.remote.notification.response.User
import com.binar.secondhand.core.domain.model.detail.Detail
import com.google.gson.annotations.SerializedName

data class Notification(
    val basePrice: String = "",
    val bidPrice: Int = 0,
    val buyerName: String = "",
    val createdAt: String ="",
    val id: Int = 0,
    val imageUrl: String = "",
    val product: Product,
    val productId: Int = 0,
    val productName: String = "",
    val read: Boolean = false,
    val receiverId: Int = 0,
    val sellerName: String = "",
    val status: String = "",
    val transactionDate: String = "",
    val updatedAt: String = "",
    val user: Detail.UserModel
)

data class Product (
   var basePrice: Int = 0,
   var createdAt: String = "",
   var description: String = "",
   var id: Int = 0,
   var imageName: String = "",
   var imageUrl: String = "",
   var location: String = "",
   var name: String = "",
   var status: String = "",
   var updatedAt: String = "",
   var userId: Int = 0
        )
