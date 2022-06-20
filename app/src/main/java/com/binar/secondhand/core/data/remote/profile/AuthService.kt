package com.binar.secondhand.core.data.remote.profile

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.profile.request.LoginRequest
import com.binar.secondhand.core.data.remote.profile.response.LoginResponse
import com.binar.secondhand.core.data.remote.profile.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthService {

    @POST("auth/login")
    fun postLogin(
        @Body request: LoginRequest
    ): SecondHandResponse<LoginResponse>

    @GET("auth/user")
    fun getUser(): SecondHandResponse<UserResponse>

    @Multipart
    @PUT("auth/user")
    fun updateUser(
        @Part("full_name") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
        @Part("address") address: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): SecondHandResponse<UserResponse>
}