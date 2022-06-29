package com.binar.secondhand.screen.detailbuyer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.usecase.detail.DetailUseCase
import com.binar.secondhand.core.event.StateEventManager

class DetailViewModel(private val useCase: DetailUseCase) : ViewModel() {

    val detailStateEvent: StateEventManager<Detail> = useCase.detailStateEventManager

    fun getDetailProduct(productId : Int){
        useCase.getDetailProduct(productId)
    }


}