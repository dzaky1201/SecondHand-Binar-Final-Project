package com.binar.secondhand.core.domain.usecase.daftar_jual

import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.event.StateEventManager

interface DaftarJualUseCase {
    val productSellerStateEventManager: StateEventManager<List<Product>>
    fun getSellerProducts()

    val sellerOrderStateEventManager: StateEventManager<List<SellerProductInterestedEntity>>
    fun getSellerOrder()

    fun closeRepository()
}