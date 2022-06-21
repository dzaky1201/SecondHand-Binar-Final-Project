package com.binar.secondhand.core.data.remote.home.source

import com.binar.secondhand.core.data.remote.home.ProductService
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.utils.ProductMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable

class HomeDataSource(private val productService: ProductService) {

    fun getProducts(): Observable<List<Product>> {
        return productService.getProducts().mapObservable {
            ProductMapper.mapProdResponseToEntity(it)
        }
    }
}