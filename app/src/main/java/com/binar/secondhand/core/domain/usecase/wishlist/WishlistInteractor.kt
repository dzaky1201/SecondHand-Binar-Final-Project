package com.binar.secondhand.core.domain.usecase.wishlist

import com.binar.secondhand.core.domain.model.wishlist.ListWishlist
import com.binar.secondhand.core.domain.repository.iWishlistRepository
import com.binar.secondhand.core.event.StateEventManager

class WishlistInteractor(private val wishlistRepos: iWishlistRepository): WishlistUseCase {
    override val listWishlistStateEventManager: StateEventManager<List<ListWishlist>> = wishlistRepos.listWishlistStateEventManager

    override fun getListWishlist() {
        wishlistRepos.getListWishlist()
    }

    override fun closeRepository() {
        wishlistRepos.close()
    }
}