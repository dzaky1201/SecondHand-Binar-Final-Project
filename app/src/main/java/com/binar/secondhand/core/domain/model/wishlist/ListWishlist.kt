package com.binar.secondhand.core.domain.model.wishlist

import com.binar.secondhand.core.data.remote.wishlist.response.Product
import com.google.gson.annotations.SerializedName

data class ListWishlist (
    val createdAt: String = "",
    val id: Int = 0,
    val product: Product,
    val productId: Int = 0,
    val updatedAt: String = "",
    val userId: Int = 0
){
    data class Product(
        var basePrice: Int = 0,
        var createdAt: String = "",
        var description: String = "",
        var id: Int = 0,
        var imageName: String = "",
        var imageUrl: String = "",
        var location: String = "",
        var name: String = "",
        var updatedAt: String = "",
        var userId: Int = 0,
        var status: String = ""
    )
}