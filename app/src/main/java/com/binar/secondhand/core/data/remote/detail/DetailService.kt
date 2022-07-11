package com.binar.secondhand.core.data.remote.detail

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.response.DetailResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrderResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrdersResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import retrofit2.http.*

interface DetailService {

    @GET("buyer/product/{id}")
    fun getDetailProduct(@Path("id") productId: Int = 0) : SecondHandResponse<DetailResponseItem>

    @POST("buyer/order")
    fun orderProduct(@Body request:OrderRequest): SecondHandResponse<OrderResponseItem>

    @GET("buyer/order")
    fun checkOrderProduct():SecondHandResponse<List<OrdersResponseItem>>
}