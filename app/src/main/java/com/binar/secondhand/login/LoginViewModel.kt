package com.binar.secondhand.login

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.domain.usecase.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager

class LoginViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {

    val loginEventManager: StateEventManager<Login> = profileUseCase.loginStateEventManager

    fun requestLogin(username: String, password: String) {
        val request = LoginRequest(username, password)
        profileUseCase.loginUser(request)
    }

    fun saveToken(login: Login) {
        profileUseCase.saveToken(login.accessToken)
    }
}