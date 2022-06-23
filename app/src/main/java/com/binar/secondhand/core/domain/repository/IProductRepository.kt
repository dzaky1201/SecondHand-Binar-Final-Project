package com.binar.secondhand.core.domain.repository

import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.event.StateEventManager
import java.io.Closeable

interface IProductRepository: Closeable {
    val productStateEventManager: StateEventManager<List<Product>>
    val categoriesStateEventManager: StateEventManager<List<Categories>>
    fun getProducts()
    fun getCategories()
}