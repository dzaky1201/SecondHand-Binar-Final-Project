package com.binar.secondhand.core.domain.usecase.home

import com.binar.secondhand.core.domain.model.home.Banner
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.PagingHome
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.event.StateEventManager

class ProductInteractor(private val productRepository: IProductRepository): ProductUseCase {
    override val productStateEventManager: StateEventManager<PagingHome<Product>> = productRepository.productStateEventManager
    override val searchStateEventManager: StateEventManager<PagingHome<Product>> = productRepository.searchStateEventManager
    override val categoriesStateEventManager: StateEventManager<List<Categories>> = productRepository.categoriesStateEventManager
    override val categoryStateEventManager: StateEventManager<PagingHome<Product>> = productRepository.categoryStateEventManager
    override val bannerStateEventManager: StateEventManager<List<Banner>>
        get() = productRepository.bannerStateEventManager

    override fun getProducts(page: Int) {
        productRepository.getProducts(page)
    }

    override fun getBannerList() {
        productRepository.getBanner()
    }

    override fun getCategories(){
        productRepository.getCategories()
    }


    override fun searchProduct(product:String, page: Int){
        productRepository.searchProduct(product, page)
    }

    override fun getCategory(categoryId: Int, page: Int) {
        productRepository.getCategory(categoryId, page)
    }
    override fun closeRepository() {
        productRepository.close()
    }
}