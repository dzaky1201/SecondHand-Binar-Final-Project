package com.binar.secondhand.core.data.remote.daftar_jual

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.daftar_jual.request.UpdateStatusProductReq
import com.binar.secondhand.core.data.remote.daftar_jual.response.SellerProductInterestedItem
import com.binar.secondhand.core.data.remote.daftar_jual.response.UpdateStatusProductResponse
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.domain.model.daftar_jual.DeleteResponse
import com.binar.secondhand.core.domain.model.daftar_jual.UpdateStatusProduct
import retrofit2.Call
import retrofit2.http.*

interface DaftarJualService {
    @GET("seller/product")
    fun getSellerProduct(): SecondHandResponse<List<ProductResponseItem>>

    @DELETE("seller/product/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<DeleteResponse>

    @GET("seller/order")
    fun getSellerOrder(@Query("status") status: String = "pending"): SecondHandResponse<List<SellerProductInterestedItem>>

    @GET("seller/order/{id}")
    fun detailSellerOrder(@Path("id") id: Int): SecondHandResponse<SellerProductInterestedItem>

    @PATCH("seller/order/{id}")
    fun updateStatusProduct(
        @Path("id") id: Int,
        @Body req: UpdateStatusProductReq
    ): SecondHandResponse<UpdateStatusProductResponse>
}