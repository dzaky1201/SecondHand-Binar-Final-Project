package com.binar.secondhand.screen.daftar_jual

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.daftar_jual.DaftarJualUseCase
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager

class SellerProducViewModel(private val daftarJualUseCase: DaftarJualUseCase, private val profileUseCase: ProfileUseCase) : ViewModel() {
    val productSellerStateEvent: StateEventManager<List<Product>> = daftarJualUseCase.productSellerStateEventManager
    val userChecked: StateEventManager<User> = profileUseCase.userStateEventManager

    fun getUser() {
        profileUseCase.getUser()
    }

    fun getProductSeller(){
        daftarJualUseCase.getSellerProducts()
    }
    override fun onCleared() {
        super.onCleared()
        daftarJualUseCase.closeRepository()
    }
}