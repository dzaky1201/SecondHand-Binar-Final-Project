package com.binar.secondhand.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.usecase.home.ProductUseCase
import com.binar.secondhand.core.event.StateEvent
import com.binar.secondhand.core.event.StateEventManager

class HomeViewModel(private val useCase: ProductUseCase) : ViewModel() {

    val productStateEvent: StateEventManager<List<Product>> = useCase.productStateEventManager

    fun getProducts(){
        useCase.getProducts()
    }


}