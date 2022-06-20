package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.remote.profile.response.LoginResponse
import com.binar.secondhand.core.data.remote.profile.response.UserResponse
import com.binar.secondhand.core.domain.model.profile.Login
import com.binar.secondhand.core.domain.model.profile.User

object ProfileMapper {

    fun mapLoginResponseToEntity(loginResponse: LoginResponse?): Login {
        return Login(
            loginResponse?.accessToken.orEmpty(),
            loginResponse?.email.orEmpty(),
            loginResponse?.name.orEmpty()
        )
    }

    fun mapUserResponseToEntity(userResponse: UserResponse?): User {
        return User(
            userResponse?.address.orEmpty(),
            userResponse?.createdAt.orEmpty(),
            userResponse?.email.orEmpty(),
            userResponse?.fullName.orEmpty(),
            userResponse?.id,
            userResponse?.imageUrl.orEmpty(),
            userResponse?.password.orEmpty(),
            userResponse?.phoneNumber.orEmpty(),
            userResponse?.updatedAt.orEmpty()
        )
    }
}