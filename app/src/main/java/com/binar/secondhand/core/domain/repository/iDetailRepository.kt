package com.binar.secondhand.core.domain.repository

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.request.WishListRequest
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.detail.OrdersProduct
import com.binar.secondhand.core.domain.model.detail.Wishlist
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.event.StateEventManager
import java.io.Closeable

interface iDetailRepository: Closeable {
    val detailStateEventManager: StateEventManager<Detail>
    val orderStateEventManager: StateEventManager<Order>
    val checkOrdersStateEventManager : StateEventManager<List<OrdersProduct>>
    val wishlistStateEventManager: StateEventManager<Wishlist>
    fun checkOrdersProduct()
    fun getProducts(productId:Int)
    fun orderProduct(request: OrderRequest)
    fun addToWishlist(request: WishListRequest)

}