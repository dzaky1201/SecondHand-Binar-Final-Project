package com.binar.secondhand.core.domain.usecase.wishlist

import com.binar.secondhand.core.domain.model.wishlist.ListWishlist
import com.binar.secondhand.core.event.StateEventManager

interface WishlistUseCase {
    val listWishlistStateEventManager: StateEventManager<List<ListWishlist>>
    fun getListWishlist()
    fun closeRepository()
}