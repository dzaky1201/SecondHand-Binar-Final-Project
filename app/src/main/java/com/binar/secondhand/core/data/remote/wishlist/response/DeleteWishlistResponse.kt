package com.binar.secondhand.core.data.remote.wishlist.response


import com.google.gson.annotations.SerializedName

data class DeleteWishlistResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)