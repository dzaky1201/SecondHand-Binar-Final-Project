package com.binar.secondhand.core.domain.repository

import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.event.StateEventManager
import java.io.Closeable

interface IProfileRepository: Closeable {
    val loginStateEventManager: StateEventManager<Login>

    fun login(request: LoginRequest)

    fun saveToken(token: String)
}