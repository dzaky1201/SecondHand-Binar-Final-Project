package com.binar.secondhand.core.domain.usecase.home

import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.event.StateEventManager

class ProductInteractor(private val productRepository: IProductRepository): ProductUseCase {
    override val productStateEventManager: StateEventManager<List<Product>> = productRepository.productStateEventManager
    override val searchStateEventManager: StateEventManager<List<Product>> = productRepository.searchStateEventManager
    override val categoriesStateEventManager: StateEventManager<List<Categories>> = productRepository.categoriesStateEventManager

    override fun getProducts() {
        productRepository.getProducts()
    }

    override fun getCategories(){
        productRepository.getCategories()
    }


    override fun searchProduct(product:String){
        productRepository.searchProduct(product)
    }

    override fun closeRepository() {
        productRepository.close()
    }
}