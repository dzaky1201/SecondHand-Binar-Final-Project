package com.binar.secondhand.core.domain.usecase

import com.binar.secondhand.core.data.source.remote.request.LoginRequest
import com.binar.secondhand.core.domain.model.Login
import com.binar.secondhand.core.domain.repository.IProfileRepository
import com.binar.secondhand.core.event.StateEventManager

class ProfileInteractor(private val profileRepository: IProfileRepository) : ProfileUseCase {
    override val loginStateEventManager: StateEventManager<Login> =
        profileRepository.loginStateEventManager

    override fun loginUser(request: LoginRequest) = profileRepository.login(request)

    override fun saveToken(token: String) = profileRepository.saveToken(token)
}