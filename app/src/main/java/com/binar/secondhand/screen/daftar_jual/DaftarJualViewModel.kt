package com.binar.secondhand.screen.daftar_jual

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.daftar_jual.DaftarJualUseCase
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager

class DaftarJualViewModel( private val profileUseCase: ProfileUseCase, private val daftarJualUseCase: DaftarJualUseCase) : ViewModel() {
    val userChecked: StateEventManager<User> = profileUseCase.userStateEventManager

    fun getUser() {
        profileUseCase.getUser()
    }

    fun getSellerProduct(){
        daftarJualUseCase.getSellerProducts()
    }

    fun getSellerOrder(){
        daftarJualUseCase.getSellerOrder()
    }

    override fun onCleared() {
        super.onCleared()
        profileUseCase.closeRepository()
    }
}