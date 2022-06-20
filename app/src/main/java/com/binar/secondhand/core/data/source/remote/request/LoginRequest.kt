package com.binar.secondhand.core.data.source.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    var email: String? = null,

    @SerializedName("password")
    var password: String? = null
)