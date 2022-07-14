package com.binar.secondhand.screen.detailbuyer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.detail.OrdersProduct
import com.binar.secondhand.core.domain.usecase.detail.DetailUseCase
import com.binar.secondhand.core.event.StateEventManager

class DetailViewModel(private val useCase: DetailUseCase) : ViewModel() {

    val detailStateEvent: StateEventManager<Detail> = useCase.detailStateEventManager
    val orderStateEvent: StateEventManager<Order> = useCase.orderStateEventManager
    val checkOrdersProductStateEvent: StateEventManager<List<OrdersProduct>> = useCase.checkOrdersProductStateEventManager

    fun getDetailProduct(productId : Int){
        useCase.getDetailProduct(productId)
    }

    fun orderProducts(request : OrderRequest){
        useCase.orderProduct(request)
    }

    fun checkOrdersProduct(){
        useCase.checkOrdersProduct()
    }




}