package com.binar.secondhand.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.home.Banner
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.PagingHome
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.usecase.home.ProductUseCase
import com.binar.secondhand.core.event.StateEvent
import com.binar.secondhand.core.event.StateEventManager

class HomeViewModel(private val useCase: ProductUseCase) : ViewModel() {

    val productStateEvent: StateEventManager<PagingHome<Product>> = useCase.productStateEventManager
    val categoriesStateEvent: StateEventManager<List<Categories>> = useCase.categoriesStateEventManager
    val searchStateEvent: StateEventManager<PagingHome<Product>> = useCase.searchStateEventManager
    val categoryStateEvent: StateEventManager<PagingHome<Product>> = useCase.categoryStateEventManager
    val bannerStateEvent: StateEventManager<List<Banner>> = useCase.bannerStateEventManager

    fun getProducts(page: Int){
        useCase.getProducts(page)
    }

    fun getBanner(){
        useCase.getBannerList()
    }

    fun getCategories(){
        useCase.getCategories()
    }

    fun searchProduct(product:String, page: Int){
        useCase.searchProduct(product, page)
    }

    fun getCategory(categoryId:Int, page: Int){
        useCase.getCategory(categoryId, page)
    }

}