package com.binar.secondhand.core.domain.model.detail

import com.google.gson.annotations.SerializedName


data class OrdersProduct (
    val basePrice: String = "",
    val buyerId: Int = 0,
    val id: Int = 0 ,
    val imageProduct: String = "",
    val price: Int = 0  ,
    val product: Product,
    val productId: Int = 0,
    val productName: String = "",
    val status: String = "",
    val transactionDate: String = "",
    val user: UserX
){


    data class Product(
        var basePrice: Int = 0,
        var description: String = "",
        var imageName: String = "",
        var imageUrl: String = "",
        var location: String = "",
        var name: String = "",
        var status: String = "",
        var user: User,
        var userId: Int = 0
    )


    data class User(
        val address: String = "",
        val city: String = "",
        val email: String = "",
        val fullName: String = "",
        val id: Int = 0,
        val phoneNumber: String = ""
    )
    data class UserX(
        var address: String = "",
        var city: String = "",
        var email: String = "",
        var fullName: String = "",
        var id: Int = 0,
        var phoneNumber: String = ""
    )
}
