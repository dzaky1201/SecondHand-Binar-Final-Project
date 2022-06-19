package com.binar.secondhand.core.domain.usecase

import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.domain.model.User
import com.binar.secondhand.core.domain.repository.IProfileRepository
import com.binar.secondhand.core.event.StateEventManager
import java.io.File

class ProfileInteractor(private val profileRepository: IProfileRepository) : ProfileUseCase {
    override val loginStateEventManager: StateEventManager<Login> =
        profileRepository.loginStateEventManager

    override val userStateEventManager: StateEventManager<User> =
        profileRepository.userStateEventManager

    override val updateUserStateEventManager: StateEventManager<User> =
        profileRepository.updateUserStateEventManager

    override fun updateUser(
        id: Int,
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        image: File
    ) {
        profileRepository.updateUser(id, fullname, email, password, address, phoneNumber, image)
    }

    override fun getUser() = profileRepository.getUser()

    override fun clearSession() {
        profileRepository.clearSession()
    }

    override fun loginUser(request: LoginRequest) = profileRepository.login(request)

    override fun saveToken(token: String) = profileRepository.saveToken(token)

    override fun closeRepository() {
        profileRepository.close()
    }
}