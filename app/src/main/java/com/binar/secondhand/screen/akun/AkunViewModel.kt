package com.binar.secondhand.screen.akun

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager
import okhttp3.internal.closeQuietly

class AkunViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {
    val userManager: StateEventManager<User> = profileUseCase.userStateEventManager

    fun getUser() {
        profileUseCase.getUser()
    }

    fun clearSession() {
        profileUseCase.clearSession()
    }

    override fun onCleared() {
        super.onCleared()
        userManager.closeQuietly()
        profileUseCase.closeRepository()
    }

}