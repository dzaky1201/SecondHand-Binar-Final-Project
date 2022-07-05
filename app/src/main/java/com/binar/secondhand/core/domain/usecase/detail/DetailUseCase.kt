package com.binar.secondhand.core.domain.usecase.detail

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.event.StateEventManager

interface DetailUseCase {
    val detailStateEventManager: StateEventManager<Detail>
    val orderStateEventManager: StateEventManager<Order>
    fun getDetailProduct(productId:Int)
    fun orderProduct(request:OrderRequest)
    fun closeRepository()
}