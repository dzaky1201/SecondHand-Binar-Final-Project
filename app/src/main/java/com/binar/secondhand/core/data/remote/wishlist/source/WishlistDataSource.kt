package com.binar.secondhand.core.data.remote.wishlist.source

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.request.WishListRequest
import com.binar.secondhand.core.data.remote.profile.request.LoginRequest
import com.binar.secondhand.core.data.remote.wishlist.WishlistService
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.detail.OrdersProduct
import com.binar.secondhand.core.domain.model.detail.Wishlist
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.profile.Login
import com.binar.secondhand.core.domain.model.wishlist.ListWishlist
import com.binar.secondhand.core.utils.*
import io.reactivex.Observable


class WishlistDataSource(private val wishlistService: WishlistService) {

    fun getListWishlist(): Observable<List<ListWishlist>> {
        return wishlistService.getListWishlist().mapObservable {
            WishlistMapper.mapWishlistResponeToEntity(it)
        }
    }

}