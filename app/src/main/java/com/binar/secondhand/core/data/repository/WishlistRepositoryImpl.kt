package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.source.DetailDataSource
import com.binar.secondhand.core.data.remote.notification.source.NotificationDataSource
import com.binar.secondhand.core.data.remote.wishlist.source.WishlistDataSource
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.model.wishlist.ListWishlist
import com.binar.secondhand.core.domain.repository.iDetailRepository
import com.binar.secondhand.core.domain.repository.iNotificationRepository
import com.binar.secondhand.core.domain.repository.iWishlistRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class WishlistRepositoryImpl(private val wishlistDataSource: WishlistDataSource): iWishlistRepository{
    private val compositeDisposable = CompositeDisposable()
    private var _listWishlistStateEventManager: MutableStateEventManager<List<ListWishlist>> = MutableStateEventManager()
    override val listWishlistStateEventManager: StateEventManager<List<ListWishlist>>
        get() = _listWishlistStateEventManager

    override fun getListWishlist() {
        compositeDisposable.add(
            wishlistDataSource.getListWishlist().fetchStateEventSubscriber { getListWishlist ->
                _listWishlistStateEventManager.post(getListWishlist)
            }
        )
    }
    override fun close() {
        _listWishlistStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }
}