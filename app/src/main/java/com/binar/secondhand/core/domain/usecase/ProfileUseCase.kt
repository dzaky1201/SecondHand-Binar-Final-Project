package com.binar.secondhand.core.domain.usecase

import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.domain.model.User
import com.binar.secondhand.core.event.StateEventManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

interface ProfileUseCase {
    val loginStateEventManager: StateEventManager<Login>
    val userStateEventManager: StateEventManager<User>

    fun loginUser(request: LoginRequest)

    fun saveToken(token: String)

    fun getUser()

    fun isUserLogon(): Boolean

    companion object : KoinComponent {
        val get: ProfileUseCase = get()
    }
}