package com.binar.secondhand.core.data.source.remote

import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.data.source.remote.response.LoginResponse
import com.binar.secondhand.core.data.source.remote.response.UserResponse
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
    @PUT("auth/user/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Part("full_name") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone_number") phoneNumber: RequestBody,
        @Part("address") address: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): SecondHandResponse<UserResponse>
}