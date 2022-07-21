package com.binar.secondhand.screen.detailbuyer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.remote.detail.DetailService
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.request.WishListRequest
import com.binar.secondhand.core.data.remote.wishlist.WishlistService
import com.binar.secondhand.core.data.remote.wishlist.response.DeleteWishlistResponse
import com.binar.secondhand.core.domain.model.daftar_jual.DeleteResponse
import com.binar.secondhand.core.domain.model.detail.*
import com.binar.secondhand.core.domain.usecase.detail.DetailUseCase
import com.binar.secondhand.core.event.StateEventManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val useCase: DetailUseCase, private val service : DetailService) : ViewModel() {

    val detailStateEvent: StateEventManager<Detail> = useCase.detailStateEventManager
    val orderStateEvent: StateEventManager<Order> = useCase.orderStateEventManager
    val checkOrdersProductStateEvent: StateEventManager<List<OrdersProduct>> =
        useCase.checkOrdersProductStateEventManager
    val wishlistStateEvent: StateEventManager<Wishlist> = useCase.wishlistStateEventManager

    private val sucessDelete: MutableLiveData<String> = MutableLiveData<String>()

    private var _deleteSuccess = MutableLiveData<DeleteOrder>()
    val deleteSuccess: LiveData<DeleteOrder> get() = _deleteSuccess

    fun getDetailProduct(productId: Int) {
        useCase.getDetailProduct(productId)
    }

    fun orderProducts(request: OrderRequest) {
        useCase.orderProduct(request)
    }

    fun checkOrdersProduct() {
        useCase.checkOrdersProduct()
    }

    fun addToWishlist(request: WishListRequest) {
        useCase.addToWishlist(request)
    }

    fun deleteOrder(id: Int) {
        service.deleteOrder(id)
            .enqueue(object : Callback<DeleteOrder> {
                override fun onResponse(
                    call: Call<DeleteOrder>,
                    response: Response<DeleteOrder>
                ) {
                    _deleteSuccess.value = response.body()
                }

                override fun onFailure(call: Call<DeleteOrder>, t: Throwable) {
                    Log.d("showError", t.message.toString())
                }

            })
    }
}