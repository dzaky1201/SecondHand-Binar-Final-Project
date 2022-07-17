package com.binar.secondhand.core.domain.usecase.daftar_jual

import com.binar.secondhand.core.data.remote.daftar_jual.request.UpdateStatusProductReq
import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.daftar_jual.UpdateStatusProduct
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.event.StateEventManager

interface DaftarJualUseCase {
    val productSellerStateEventManager: StateEventManager<List<Product>>
    fun getSellerProducts()

    val sellerOrderStateEventManager: StateEventManager<List<SellerProductInterestedEntity>>
    fun getSellerOrder(status: String)

    val sellerOrderDetailStateEventManager: StateEventManager<SellerProductInterestedEntity>
    fun getDetailSellerOrder(id: Int)

    val updateStatusOrderEvent: StateEventManager<UpdateStatusProduct>
    fun updateStatusOrder(id: Int, req: UpdateStatusProductReq)

    fun closeRepository()
}