package com.binar.secondhand.core.domain.repository

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.model.wishlist.ListWishlist
import com.binar.secondhand.core.event.StateEventManager
import java.io.Closeable

interface iWishlistRepository: Closeable {
    val listWishlistStateEventManager: StateEventManager<List<ListWishlist>>
    fun getListWishlist()
}