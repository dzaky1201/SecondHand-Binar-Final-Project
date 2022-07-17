package com.binar.secondhand.core.domain.usecase.daftar_jual

import com.binar.secondhand.core.data.remote.daftar_jual.request.UpdateStatusProductReq
import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.daftar_jual.UpdateStatusProduct
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

    override fun getSellerOrder(status: String) {
        repository.getSellerOrder(status)
    }

    override val sellerOrderDetailStateEventManager: StateEventManager<SellerProductInterestedEntity>
        get() = repository.sellerOrderDetailStateEventManager

    override fun getDetailSellerOrder(id: Int) {
        repository.getDetailSellerOrder(id)
    }

    override val updateStatusOrderEvent: StateEventManager<UpdateStatusProduct>
        get() = repository.updateStatusOrderEvent

    override fun updateStatusOrder(id: Int, req: UpdateStatusProductReq) {
        repository.updateStatusOrder(id, req)
    }

    override fun closeRepository() {
        repository.close()
    }
}