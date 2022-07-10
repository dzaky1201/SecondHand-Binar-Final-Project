package com.binar.secondhand.core.data.remote.daftar_jual.source

import com.binar.secondhand.core.data.remote.daftar_jual.DaftarJualService
import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.utils.DaftarJualMapper
import com.binar.secondhand.core.utils.JualMapper
import com.binar.secondhand.core.utils.ProductMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable

class DaftarJualSource(private val service: DaftarJualService) {
    fun getProductSeller(): Observable<List<Product>>{
        return service.getSellerProduct().mapObservable {
            ProductMapper.mapProdResponseToEntity(it)
        }
    }

    fun getSellerOrder(): Observable<List<SellerProductInterestedEntity>>{
        return service.getSellerOrder().mapObservable {
            DaftarJualMapper.sellerOrderResponseToEntity(it)
        }
    }
}