package com.binar.secondhand.update_akun

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.User
import com.binar.secondhand.core.domain.usecase.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager
import java.io.File

class UpdateAkunViewModel(private val useCase: ProfileUseCase) : ViewModel() {
    val updateUserManager: StateEventManager<User> = useCase.updateUserStateEventManager

    fun updateUser(
        id: Int,
        fullname: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String,
        file: File
    ){
        useCase.updateUser(id, fullname, email, password, address, phoneNumber, file)
    }
}