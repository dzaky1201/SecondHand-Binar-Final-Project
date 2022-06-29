package com.binar.secondhand.core.data.remote.detail.source

import com.binar.secondhand.core.data.remote.detail.DetailService
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.utils.DetailMapper
import com.binar.secondhand.core.utils.ProductMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable


class DetailDataSource(private val detailService: DetailService) {

    fun getDetail(productId : Int): Observable<Detail> {
        return detailService.getDetailProduct(productId).mapObservable {
            DetailMapper.mapDetailResponseToEntity(it)
        }
    }
}