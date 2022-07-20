package com.binar.secondhand.core.data.remote.home

import com.binar.secondhand.core.data.network.SecondHandPagingResponse
import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.home.response.BannerResponseItem
import com.binar.secondhand.core.data.remote.home.response.CategoriesResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("buyer/product")
    fun getProducts(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("status") available: String = "available"
    ): SecondHandResponse<List<ProductResponseItem>>

    @GET("seller/category")
    fun getCategories(): SecondHandResponse<List<CategoriesResponseItem>>

    @GET("buyer/product")
    fun searchProduct(
        @Query("search") search: String = "",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("status") available: String = "available"
    ): SecondHandResponse<List<ProductResponseItem>>

    @GET("buyer/product")
    fun getCategory(
        @Query("category_id") category: Int = 0,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("status") available: String = "available"
    ): SecondHandResponse<List<ProductResponseItem>>

    @GET("seller/banner")
    fun getBanner(): SecondHandResponse<List<BannerResponseItem>>
}