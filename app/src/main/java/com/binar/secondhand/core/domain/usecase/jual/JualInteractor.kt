package com.binar.secondhand.core.domain.usecase.jual

import com.binar.secondhand.core.data.remote.jual.request.SellerProductRequest
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.domain.repository.IJualRepository
import com.binar.secondhand.core.event.StateEventManager
import okhttp3.internal.closeQuietly
import java.io.File

class JualInteractor(private val jualRepository: IJualRepository): JualUseCase {
    override fun postProduct(sellerProductRequest: SellerProductRequest, file: File) {
       jualRepository.postProduct(sellerProductRequest, file)
    }

    override val productPostStateEventManager: StateEventManager<SellerProduct>
        get() =  jualRepository.productPostStateEventManager

    override fun closeRepository() {
        jualRepository.close()
    }
}