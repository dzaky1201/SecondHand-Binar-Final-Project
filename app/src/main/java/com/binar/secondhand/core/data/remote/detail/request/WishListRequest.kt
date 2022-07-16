package com.binar.secondhand.core.data.remote.detail.request

import com.google.gson.annotations.SerializedName

data class WishListRequest(
    @SerializedName("product_id")
    var productId: Int? = null,
)