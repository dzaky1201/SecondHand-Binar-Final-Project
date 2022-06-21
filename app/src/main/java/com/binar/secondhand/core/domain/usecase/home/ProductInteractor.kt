package com.binar.secondhand.core.domain.usecase.home

import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.event.StateEventManager

class ProductInteractor(private val productRepository: IProductRepository): ProductUseCase {
    override val productStateEventManager: StateEventManager<List<Product>> = productRepository.productStateEventManager

    override fun getProducts() {
        productRepository.getProducts()
    }

    override fun closeRepository() {
        productRepository.close()
    }
}