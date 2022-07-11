package com.binar.secondhand.core.domain.usecase.detail

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.detail.OrdersProduct
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.domain.repository.iDetailRepository
import com.binar.secondhand.core.domain.usecase.home.ProductUseCase
import com.binar.secondhand.core.event.StateEventManager

class Detailinteractor(private val detailRepos: iDetailRepository): DetailUseCase {
    override val detailStateEventManager: StateEventManager<Detail> = detailRepos.detailStateEventManager
    override val orderStateEventManager: StateEventManager<Order> = detailRepos.orderStateEventManager
    override val checkOrdersProductStateEventManager : StateEventManager<List<OrdersProduct>> = detailRepos.checkOrdersStateEventManager
    override fun getDetailProduct(productId:Int) {
       detailRepos.getProducts(productId)
    }

    override fun orderProduct(request: OrderRequest) {
        detailRepos.orderProduct(request)
    }

    override fun checkOrdersProduct() {
        detailRepos.checkOrdersProduct()
    }
    override fun closeRepository() {
        detailRepos.close()
    }
}