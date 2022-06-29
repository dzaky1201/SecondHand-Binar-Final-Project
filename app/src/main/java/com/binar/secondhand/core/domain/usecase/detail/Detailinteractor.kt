package com.binar.secondhand.core.domain.usecase.detail

import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.domain.repository.iDetailRepository
import com.binar.secondhand.core.domain.usecase.home.ProductUseCase
import com.binar.secondhand.core.event.StateEventManager

class Detailinteractor(private val detailRepos: iDetailRepository): DetailUseCase {
    override val detailStateEventManager: StateEventManager<Detail> = detailRepos.detailStateEventManager

    override fun getDetailProduct(productId:Int) {
       detailRepos.getProducts(productId)
    }

    override fun closeRepository() {
        detailRepos.close()
    }
}