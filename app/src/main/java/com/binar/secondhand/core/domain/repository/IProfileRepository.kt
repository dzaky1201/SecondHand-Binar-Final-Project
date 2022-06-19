package com.binar.secondhand.core.domain.repository

import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.domain.model.User
import com.binar.secondhand.core.event.StateEventManager
import java.io.Closeable
import java.io.File

interface IProfileRepository: Closeable {
    val loginStateEventManager: StateEventManager<Login>
    val userStateEventManager: StateEventManager<User>
    val updateUserStateEventManager: StateEventManager<User>

    fun login(request: LoginRequest)

    fun saveToken(token: String)

    fun getUser()

    fun clearSession()
    fun updateUser(
        id: Int,
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        image: File
    )
}