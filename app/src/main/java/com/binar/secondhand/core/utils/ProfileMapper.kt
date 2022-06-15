package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.source.remote.response.LoginResponse
import com.binar.secondhand.core.domain.model.Login

object ProfileMapper {

    fun mapLoginResponseToEntity(loginResponse: LoginResponse?): Login {
        return Login(
            loginResponse?.accessToken.orEmpty(),
            loginResponse?.email.orEmpty(),
            loginResponse?.name.orEmpty()
        )
    }
}