package com.binar.secondhand.core.data.remote.detail

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.request.WishListRequest
import com.binar.secondhand.core.data.remote.detail.response.DetailResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrderResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrdersResponseItem
import com.binar.secondhand.core.data.remote.detail.response.WishListResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.domain.model.daftar_jual.DeleteResponse
import com.binar.secondhand.core.domain.model.detail.DeleteOrder
import retrofit2.Call
import retrofit2.http.*

interface DetailService {

    @GET("buyer/product/{id}")
    fun getDetailProduct(@Path("id") productId: Int = 0) : SecondHandResponse<DetailResponseItem>

    @POST("buyer/order")
    fun orderProduct(@Body request:OrderRequest): SecondHandResponse<OrderResponseItem>

    @GET("buyer/order")
    fun checkOrderProduct():SecondHandResponse<List<OrdersResponseItem>>

    @POST("buyer/wishlist")
    fun addToWishlist(@Body request: WishListRequest): SecondHandResponse<WishListResponseItem>

    @DELETE("buyer/order/{id}")
    fun deleteOrder(@Path("id") id: Int): Call<DeleteOrder>
}