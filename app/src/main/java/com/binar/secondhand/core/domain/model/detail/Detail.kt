package com.binar.secondhand.core.domain.model.detail

import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product

data class Detail(

    val id: Int? = 0,
    val name: String? = "",
    val description: String? = "",
    val base_price: Int? = 0,
    val image_url: String? ="",
    val image_name: String? ="",
    val location:String? ="",
    val user_id: Int? = 0,
    val status:String? = "",
    val createdAt:String? = "",
    val updatedAt: String? = "",
    val Categories: List<Category>,
    val user: UserModel
        ){
    data class UserModel(
        var address : String? = "",
        var city: String? = "",
        var email: String? = "",
        var fullname: String? = "",
        var id : Int? = 0,
        var imageUrl: String?= "",
        var phoneNumber:String = ""
    )

    data class Category(
        val id: Int? = 0,
        val name: String? = ""
    )
}


