package com.binar.secondhand.core.domain.repository

import com.binar.secondhand.core.data.remote.jual.request.SellerProductRequest
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.event.StateEventManager
import java.io.Closeable
import java.io.File

interface IJualRepository: Closeable {

    fun postProduct(sellerProductRequest: SellerProductRequest, file: File)
    val productPostStateEventManager : StateEventManager<SellerProduct>
}