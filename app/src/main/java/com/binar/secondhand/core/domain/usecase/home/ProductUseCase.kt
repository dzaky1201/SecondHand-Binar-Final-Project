package com.binar.secondhand.core.domain.usecase.home

import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.event.StateEventManager

interface ProductUseCase {
    val productStateEventManager: StateEventManager<List<Product>>
    val categoriesStateEventManager: StateEventManager<List<Categories>>
    val searchStateEventManager: StateEventManager<List<Product>>
    val categoryStateEventManager: StateEventManager<List<Product>>
    fun getProducts()
    fun getCategories()
    fun searchProduct(product:String)
    fun getCategory(categoryId:Int)
    fun closeRepository()
}