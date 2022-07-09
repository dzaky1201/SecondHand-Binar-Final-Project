package com.binar.secondhand.screen.jual.add_product

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
import java.io.File

class AddProductViewModel(private val jualUseCase: JualUseCase, private val productUseCase: ProductUseCase) : ViewModel() {

    val stateCategories: StateEventManager<List<Categories>> = productUseCase.categoriesStateEventManager
    val productPostStateEventManager : StateEventManager<SellerProduct> = jualUseCase.productPostStateEventManager

    fun postProduct(sellerProductRequest: SellerProductRequest, file: File){
        jualUseCase.postProduct(sellerProductRequest, file)
    }

    fun getCategories(){
        productUseCase.getCategories()
    }

}