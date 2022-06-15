package com.binar.secondhand.core.data.source.remote

import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.data.source.remote.response.LoginResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    fun postLogin(
        @Body request: LoginRequest
    ): SecondHandResponse<LoginResponse>
}