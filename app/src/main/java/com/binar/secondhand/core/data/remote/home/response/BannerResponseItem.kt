package com.binar.secondhand.core.data.remote.home.response


import com.google.gson.annotations.SerializedName

data class BannerResponseItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String?
)