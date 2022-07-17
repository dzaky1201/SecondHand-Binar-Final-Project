package com.binar.secondhand.screen.daftar_jual

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.daftar_jual.DaftarJualUseCase
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager

class TerjualViewModel(private val userCase: DaftarJualUseCase, private val profileUseCase: ProfileUseCase) : ViewModel() {

    val sellerOrder = userCase.sellerOrderStateEventManager

    fun getSellerOrder(status: String){
        userCase.getSellerOrder(status)
    }

    val userChecked: StateEventManager<User> = profileUseCase.userStateEventManager

    fun getUser() {
        profileUseCase.getUser()
    }
}