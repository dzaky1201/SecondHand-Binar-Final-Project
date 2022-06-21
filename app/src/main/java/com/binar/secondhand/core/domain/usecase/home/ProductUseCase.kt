package com.binar.secondhand.core.domain.usecase.home

import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.event.StateEventManager

interface ProductUseCase {
    val productStateEventManager: StateEventManager<List<Product>>
    fun getProducts()
    fun closeRepository()
}