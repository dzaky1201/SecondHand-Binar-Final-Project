package com.binar.secondhand.core.data.remote.wishlist

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.request.WishListRequest
import com.binar.secondhand.core.data.remote.detail.response.DetailResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrderResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrdersResponseItem
import com.binar.secondhand.core.data.remote.detail.response.WishListResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.data.remote.wishlist.response.DeleteWishlistResponse
import com.binar.secondhand.core.data.remote.wishlist.response.ListWishlistResponseItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*



interface WishlistService {

    @GET("buyer/wishlist")
    fun getListWishlist():SecondHandResponse<List<ListWishlistResponseItem>>

    @DELETE("buyer/wishlist/{id}")
    fun deleteWishlist(@Path("id") id : Int):Call<DeleteWishlistResponse>

}