package com.binar.secondhand.core.data.remote.home.response


import com.google.gson.annotations.SerializedName

data class ProductResponseItem(
    @SerializedName("base_price")
    val basePrice: Int? = 0,
    @SerializedName("Categories")
    val categories: List<Category>?,
    @SerializedName("createdAt")
    val createdAt: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image_name")
    val imageName: String? = "",
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @SerializedName("location")
    val location: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("updatedAt")
    val updatedAt: String? = "",
    @SerializedName("user_id")
    val userId: Int? = 0
){
    data class Category(
        @SerializedName("id")
        val id: Int? = 0,
        @SerializedName("name")
        val name: String? = ""
    )
}