package com.binar.secondhand.core.data.remote.daftar_jual.request

import com.google.gson.annotations.SerializedName

data class UpdateStatusProductReq(
    @SerializedName("status")
    var status: String? = null,
)