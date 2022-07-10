package com.binar.secondhand.core.data.remote.daftar_jual

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.daftar_jual.response.SellerProductInterestedItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.data.remote.jual.response.SellerProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DaftarJualService {
    @GET("seller/product")
    fun getSellerProduct() : SecondHandResponse<List<ProductResponseItem>>

    @GET("seller/order")
    fun getSellerOrder(@Query("status") status: String = "pending") : SecondHandResponse<List<SellerProductInterestedItem>>
}