package com.binar.secondhand.core.data.remote.jual.request


data class SellerProductRequest(
    var name: String? = null,
    var basePrice: String? = null,
    var categorId: List<String>,
    var location: String? = null,
    var description: String? = null
)
