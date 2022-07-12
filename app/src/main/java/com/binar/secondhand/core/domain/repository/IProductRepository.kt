package com.binar.secondhand.core.domain.repository

import com.binar.secondhand.core.domain.model.home.Banner
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.PagingHome
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.event.StateEventManager
import java.io.Closeable

interface IProductRepository: Closeable {
    val productStateEventManager: StateEventManager<PagingHome<Product>>
    val categoriesStateEventManager: StateEventManager<List<Categories>>
    val searchStateEventManager : StateEventManager<PagingHome<Product>>
    val categoryStateEventManager : StateEventManager<PagingHome<Product>>
    val bannerStateEventManager : StateEventManager<List<Banner>>
    fun getProducts(page: Int)
    fun getCategories()
    fun getBanner()
    fun searchProduct(product:String, page: Int)
    fun getCategory(categoryId:Int)
}