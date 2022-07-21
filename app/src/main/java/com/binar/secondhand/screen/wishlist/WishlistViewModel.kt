package com.binar.secondhand.screen.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.remote.wishlist.WishlistAPI
import com.binar.secondhand.core.data.remote.wishlist.WishlistService
import com.binar.secondhand.core.data.remote.wishlist.response.DeleteWishlistResponse
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.model.wishlist.ListWishlist
import com.binar.secondhand.core.domain.usecase.notification.NotificationUseCase
import com.binar.secondhand.core.domain.usecase.wishlist.WishlistUseCase
import com.binar.secondhand.core.event.StateEventManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WishlistViewModel(private val useCase : WishlistUseCase, private val service : WishlistService) : ViewModel() {

    val listWishlistStateEvent : StateEventManager<List<ListWishlist>> = useCase.listWishlistStateEventManager
    private val sucessDelete : MutableLiveData<String> = MutableLiveData<String>()

    fun getListWishlist(){
        useCase.getListWishlist()
    }

    fun setStringSucess():LiveData<String>{
        return sucessDelete
    }
    fun deleteWishlist(id : Int){
        service.deleteWishlist(id)

    }
}