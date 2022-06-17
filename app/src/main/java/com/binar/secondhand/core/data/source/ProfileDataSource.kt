package com.binar.secondhand.core.data.source

import com.binar.secondhand.core.data.source.remote.AuthService
import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.data.source.remote.response.UserResponse
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.domain.model.User
import com.binar.secondhand.core.utils.ProfileMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable

class ProfileDataSource(private val authService: AuthService) {

    fun postLogin(request: LoginRequest): Observable<Login>{
        return authService.postLogin(request).mapObservable {
            ProfileMapper.mapLoginResponseToEntity(it)
        }
    }

    fun getUser(): Observable<User>{
        return authService.getUser().mapObservable {
            ProfileMapper.mapUserResponseToEntity(it)
        }
    }
}