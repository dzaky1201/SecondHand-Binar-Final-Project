package com.binar.secondhand.core.domain.usecase

import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.event.StateEventManager

interface ProfileUseCase {
    val loginStateEventManager: StateEventManager<Login>

    fun loginUser(request: LoginRequest)

    fun saveToken(token: String)
}