package com.binar.secondhand.core.domain.usecase.jual

import com.binar.secondhand.core.data.remote.jual.request.SellerProductRequest
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.event.StateEventManager
import java.io.File

interface JualUseCase {

    fun postProduct(sellerProductRequest: SellerProductRequest, file: File)
    val productPostStateEventManager : StateEventManager<SellerProduct>
    fun closeRepository()
}