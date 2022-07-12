package com.binar.secondhand.core.domain.usecase.home

import com.binar.secondhand.core.domain.model.home.Banner
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.PagingHome
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.event.StateEventManager

interface ProductUseCase {
    val productStateEventManager: StateEventManager<PagingHome<Product>>
    val categoriesStateEventManager: StateEventManager<List<Categories>>
    val searchStateEventManager: StateEventManager<PagingHome<Product>>
    val categoryStateEventManager: StateEventManager<PagingHome<Product>>
    val bannerStateEventManager : StateEventManager<List<Banner>>
    fun getProducts(page: Int)
    fun getBannerList()
    fun getCategories()
    fun searchProduct(product:String, page: Int)
    fun getCategory(categoryId:Int)
    fun closeRepository()
}