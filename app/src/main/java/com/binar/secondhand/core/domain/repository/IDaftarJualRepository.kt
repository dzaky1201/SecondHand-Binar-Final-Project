package com.binar.secondhand.core.domain.repository

import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.event.StateEventManager
import java.io.Closeable

interface IDaftarJualRepository: Closeable {
    val productSellerStateEventManager: StateEventManager<List<Product>>
    fun getSellerProducts()

    val sellerOrderStateEventManager: StateEventManager<List<SellerProductInterestedEntity>>
    fun getSellerOrder()

    val sellerOrderDetailStateEventManager: StateEventManager<SellerProductInterestedEntity>
    fun getDetailSellerOrder(id: Int)
}