package com.binar.secondhand.screen.daftar_jual

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.network.RetrofitProvider
import com.binar.secondhand.core.data.remote.daftar_jual.DaftarJualService
import com.binar.secondhand.core.domain.model.daftar_jual.DeleteResponse
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.daftar_jual.DaftarJualUseCase
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerProducViewModel(
    private val daftarJualUseCase: DaftarJualUseCase,
    private val profileUseCase: ProfileUseCase,
    private val service: DaftarJualService,
) : ViewModel() {
    val productSellerStateEvent: StateEventManager<List<Product>> =
        daftarJualUseCase.productSellerStateEventManager
    val userChecked: StateEventManager<User> = profileUseCase.userStateEventManager
    private val isSuccess : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var _deleteSuccess = MutableLiveData<DeleteResponse>()
    val deleteSuccess: LiveData<DeleteResponse> get() = _deleteSuccess

    private var _deleteFailed = MutableLiveData<DeleteResponse>()
    val deleteFailed: LiveData<DeleteResponse> get() = _deleteFailed

    fun getIsSuccess(): LiveData<Boolean>{
        return isSuccess
    }
    fun getUser() {
        profileUseCase.getUser()
    }

    fun getProductSeller() {
        daftarJualUseCase.getSellerProducts()
    }

    fun deleteProduct(id: Int) {
        service.deleteProduct(id)
            .enqueue(object : Callback<DeleteResponse> {
                override fun onResponse(
                    call: Call<DeleteResponse>,
                    response: Response<DeleteResponse>
                ) {
                    _deleteSuccess.value = response.body()
                    isSuccess.value = true
                    Log.d("Delete","Success")
                }

                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                    isSuccess.value = false

                    Log.d("Delete","Failure")
                    Log.d("showError", t.message.toString())
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        daftarJualUseCase.closeRepository()
    }
}