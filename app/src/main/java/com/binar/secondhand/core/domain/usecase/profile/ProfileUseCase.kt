package com.binar.secondhand.core.domain.usecase.profile

import com.binar.secondhand.core.data.remote.profile.request.LoginRequest
import com.binar.secondhand.core.domain.model.profile.Login
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.event.StateEventManager
import java.io.File

interface ProfileUseCase {
    val loginStateEventManager: StateEventManager<Login>
    val userStateEventManager: StateEventManager<User>
    val updateUserStateEventManager: StateEventManager<User>
    val registerUserStateEventManager: StateEventManager<User>

    fun loginUser(request: LoginRequest)

    fun saveToken(token: String)

    fun getUser()

    fun clearSession()

    fun updateUser(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        city: String
    )

    fun updateUserWithImage(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        city: String,
        file: File
    )

    fun registerUser(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        city: String,
        file: File
    )

    fun closeRepository()
}