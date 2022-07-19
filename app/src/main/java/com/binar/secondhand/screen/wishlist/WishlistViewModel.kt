package com.binar.secondhand.screen.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.model.wishlist.ListWishlist
import com.binar.secondhand.core.domain.usecase.notification.NotificationUseCase
import com.binar.secondhand.core.domain.usecase.wishlist.WishlistUseCase
import com.binar.secondhand.core.event.StateEventManager

class WishlistViewModel(private val useCase : WishlistUseCase) : ViewModel() {

    val listWishlistStateEvent : StateEventManager<List<ListWishlist>> = useCase.listWishlistStateEventManager

    fun getListWishlist(){
        useCase.getListWishlist()
    }
}