package com.binar.secondhand.screen.update_akun

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager
import java.io.File

class UpdateAkunViewModel(private val useCase: ProfileUseCase) : ViewModel() {
    val updateUserManager: StateEventManager<User> = useCase.updateUserStateEventManager

    fun updateUser(
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        file: File
    ){
        useCase.updateUser(fullname, email, password, address, phoneNumber, file)
    }
}