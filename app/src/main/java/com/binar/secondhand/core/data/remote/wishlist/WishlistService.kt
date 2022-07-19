package com.binar.secondhand.core.data.remote.wishlist

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.request.WishListRequest
import com.binar.secondhand.core.data.remote.detail.response.DetailResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrderResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrdersResponseItem
import com.binar.secondhand.core.data.remote.detail.response.WishListResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.data.remote.wishlist.response.ListWishlistResponseItem
import retrofit2.http.*

interface WishlistService {

    @GET("buyer/wishlist")
    fun getListWishlist():SecondHandResponse<List<ListWishlistResponseItem>>

}