package com.binar.secondhand.core.data.remote.daftar_jual.response


import com.google.gson.annotations.SerializedName

data class UpdateStatusProductResponse(
    @SerializedName("base_price")
    val basePrice: String?,
    @SerializedName("buyer_id")
    val buyerId: Int?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_product")
    val imageProduct: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("product_name")
    val productName: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("transaction_date")
    val transactionDate: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)