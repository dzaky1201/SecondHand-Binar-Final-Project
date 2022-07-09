package com.binar.secondhand.core.data.remote.jual

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.jual.response.SellerProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface JualService {

    @Multipart
    @POST("seller/product")
    fun postProduct(
        @PartMap partBody: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part("category_ids") categoryId: List<@JvmSuppressWildcards RequestBody>,
        @Part imageProduct: MultipartBody.Part? = null
    ): SecondHandResponse<SellerProductResponse>
}