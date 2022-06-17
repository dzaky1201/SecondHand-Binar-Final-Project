package com.binar.secondhand.akun

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.User
import com.binar.secondhand.core.domain.usecase.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager
import okhttp3.internal.closeQuietly

class AkunViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {
    val userManager: StateEventManager<User> = profileUseCase.userStateEventManager

    fun getUser(){
        profileUseCase.getUser()
    }

    override fun onCleared() {
        super.onCleared()
        userManager.closeQuietly()
    }

}