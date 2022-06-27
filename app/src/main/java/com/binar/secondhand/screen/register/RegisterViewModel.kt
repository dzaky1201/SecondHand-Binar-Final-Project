package com.binar.secondhand.screen.register

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager
import java.io.File

class RegisterViewModel(private val profileUseCase: ProfileUseCase) : ViewModel() {
    val registerUserManager: StateEventManager<User> = profileUseCase.registerUserStateEventManager

    fun registerUser(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        city: String,
        file: File
    ) {
        profileUseCase.registerUser(fullname, email, password, address, phoneNumber, city, file)
    }
}