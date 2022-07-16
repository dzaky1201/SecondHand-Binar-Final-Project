package com.binar.secondhand.core.data.remote.detail.response


import com.google.gson.annotations.SerializedName

data class WishListResponseItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("Product")
    val product: Product
)