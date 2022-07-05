package com.binar.secondhand.core.data.remote.detail.request

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("product_id")
    var productId: Int? = null,

    @SerializedName("bid_price")
    var bidPrice: Int? = null
)