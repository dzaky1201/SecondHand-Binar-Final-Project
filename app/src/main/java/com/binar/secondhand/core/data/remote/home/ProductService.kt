package com.binar.secondhand.core.data.remote.home

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.home.response.CategoriesResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("buyer/product")
    fun getProducts(): SecondHandResponse<List<ProductResponseItem>>

    @GET("seller/category")
    fun getCategories(): SecondHandResponse<List<CategoriesResponseItem>>

    @GET("buyer/product")
    fun searchProduct(@Query("search") search: String = ""): SecondHandResponse<List<ProductResponseItem>>

    @GET("buyer/product")
    fun getCategory(@Query("category_id") category: Int = 0): SecondHandResponse<List<ProductResponseItem>>
}