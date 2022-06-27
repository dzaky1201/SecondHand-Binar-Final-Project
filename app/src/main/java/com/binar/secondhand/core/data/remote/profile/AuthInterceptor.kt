package com.binar.secondhand.core.data.remote.profile

import com.binar.secondhand.core.data.local.DataPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val preferences = DataPreferences.get
        val token = preferences.token

        val request = chain
            .request()
            .newBuilder()
            .addHeader("access_token", token)
            .build()
        return chain.proceed(request)
    }
}