package com.binar.secondhand.core.domain.usecase.daftar_jual

import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IDaftarJualRepository
import com.binar.secondhand.core.event.StateEventManager

class DaftarJualInteractor(private val repository: IDaftarJualRepository): DaftarJualUseCase {
    override val productSellerStateEventManager: StateEventManager<List<Product>>
        get() = repository.productSellerStateEventManager

    override fun getSellerProducts() {
        repository.getSellerProducts()
    }

    override val sellerOrderStateEventManager: StateEventManager<List<SellerProductInterestedEntity>>
        get() = repository.sellerOrderStateEventManager

    override fun getSellerOrder() {
        repository.getSellerOrder()
    }

    override val sellerOrderDetailStateEventManager: StateEventManager<SellerProductInterestedEntity>
        get() = repository.sellerOrderDetailStateEventManager

    override fun getDetailSellerOrder(id: Int) {
        repository.getDetailSellerOrder(id)
    }

    override fun closeRepository() {
        repository.close()
    }
}