package com.binar.secondhand.screen.wishlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.remote.wishlist.WishlistService
import com.binar.secondhand.core.data.remote.wishlist.response.DeleteWishlistResponse
import com.binar.secondhand.core.domain.model.daftar_jual.DeleteResponse
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

    private var _deleteSuccess = MutableLiveData<DeleteWishlistResponse>()
    val deleteSuccess: LiveData<DeleteWishlistResponse> get() = _deleteSuccess
    fun getListWishlist(){
        useCase.getListWishlist()
    }

    fun setStringSucess():LiveData<String>{
        return sucessDelete
    }
    fun deleteWishlist(id : Int){
        service.deleteWishlist(id).enqueue(object : Callback<DeleteWishlistResponse>{
            override fun onResponse(
                call: Call<DeleteWishlistResponse>,
                response: Response<DeleteWishlistResponse>
            ) {
                _deleteSuccess.value = response.body()
            }

            override fun onFailure(call: Call<DeleteWishlistResponse>, t: Throwable) {
                Log.d("showError", t.message.toString())
            }

        })

    }

}