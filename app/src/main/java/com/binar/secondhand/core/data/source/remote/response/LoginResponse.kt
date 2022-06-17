package com.binar.secondhand.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("name")
    val name: String?
)