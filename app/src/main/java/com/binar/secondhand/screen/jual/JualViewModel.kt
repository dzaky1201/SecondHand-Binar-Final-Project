package com.binar.secondhand.screen.jual

import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.remote.jual.request.SellerProductRequest
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.usecase.home.ProductUseCase
import com.binar.secondhand.core.domain.usecase.jual.JualUseCase
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import com.binar.secondhand.core.event.StateEventManager
import okhttp3.internal.closeQuietly

class JualViewModel(private val productUseCase: ProductUseCase, private val profileUseCase: ProfileUseCase) : ViewModel() {

    val stateCategories: StateEventManager<List<Categories>> = productUseCase.categoriesStateEventManager
    val usertStateEventManager : StateEventManager<User> = profileUseCase.userStateEventManager

    fun getCategories(){
        productUseCase.getCategories()
    }

    fun getUser() {
        profileUseCase.getUser()
    }

    override fun onCleared() {
        super.onCleared()
        stateCategories.closeQuietly()
        usertStateEventManager.closeQuietly()
        productUseCase.closeRepository()
        profileUseCase.closeRepository()
    }
}