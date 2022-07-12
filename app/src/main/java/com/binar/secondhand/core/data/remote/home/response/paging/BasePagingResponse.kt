package com.binar.secondhand.core.data.remote.home.response.paging


import com.google.gson.annotations.SerializedName

data class BasePagingResponse<T>(
    @SerializedName("data")
    val `data`: List<T>?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?
)