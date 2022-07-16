package com.binar.secondhand.core.domain.model.detail

data class Wishlist (
    val name: String = "",
    val product: Product
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
        var userId: Int = 0
    )
}

