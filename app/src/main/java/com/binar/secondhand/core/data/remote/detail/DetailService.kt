package com.binar.secondhand.core.data.remote.detail

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.detail.response.DetailResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailService {

    @GET("buyer/product/{id}")
    fun getDetailProduct(@Path("id") productId: Int = 0) : SecondHandResponse<DetailResponseItem>
}