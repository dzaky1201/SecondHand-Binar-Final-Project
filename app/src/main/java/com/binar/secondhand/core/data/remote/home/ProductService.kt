package com.binar.secondhand.core.data.remote.home

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("buyer/product")
    fun getProducts(
//        @Query("status") status: String = "",
//        @Query("category_id") categoryId: Int = 0,
//        @Query("search") search: String = ""
    ): SecondHandResponse<List<ProductResponseItem>>
}