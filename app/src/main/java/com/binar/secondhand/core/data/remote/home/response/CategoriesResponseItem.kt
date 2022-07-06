package com.binar.secondhand.core.data.remote.home.response

import com.google.gson.annotations.SerializedName


data class CategoriesResponseItem(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null
)