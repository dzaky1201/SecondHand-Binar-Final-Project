package com.binar.secondhand.screen.daftar_jual.screen.diminati

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.remote.daftar_jual.request.UpdateStatusProductReq
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.daftar_jual.DaftarJualUseCase
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager

class DiminatiViewModel(private val userCase: DaftarJualUseCase, private val profileUseCase: ProfileUseCase) : ViewModel() {

    val sellerOrder = userCase.sellerOrderStateEventManager
    val sellerDetailOrder = userCase.sellerOrderDetailStateEventManager
    val updateStatusOrder = userCase.updateStatusOrderEvent
    val userChecked: StateEventManager<User> = profileUseCase.userStateEventManager

    fun getUser() {
        profileUseCase.getUser()
    }

    fun getSellerOrder(status: String){
        userCase.getSellerOrder(status)
    }

    fun getDetailSellerOrder(id: Int){
        userCase.getDetailSellerOrder(id)
    }

    fun updateStatusOrder(id: Int, req: UpdateStatusProductReq){
        userCase.updateStatusOrder(id, req)
    }

    override fun onCleared() {
        super.onCleared()
        userCase.closeRepository()
        profileUseCase.closeRepository()
    }
}