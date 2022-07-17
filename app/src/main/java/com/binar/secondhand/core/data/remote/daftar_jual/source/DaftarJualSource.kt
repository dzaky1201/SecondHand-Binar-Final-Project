package com.binar.secondhand.core.data.remote.daftar_jual.source

import com.binar.secondhand.core.data.remote.daftar_jual.DaftarJualService
import com.binar.secondhand.core.data.remote.daftar_jual.request.UpdateStatusProductReq
import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.daftar_jual.UpdateStatusProduct
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.utils.DaftarJualMapper
import com.binar.secondhand.core.utils.ProductMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable

class DaftarJualSource(private val service: DaftarJualService) {
    fun getProductSeller(): Observable<List<Product>> {
        return service.getSellerProduct().mapObservable {
            ProductMapper.mapProdResponseToEntity(it)
        }
    }

    fun getSellerOrder(status: String): Observable<List<SellerProductInterestedEntity>> {
        return service.getSellerOrder(status).mapObservable {
            DaftarJualMapper.sellerOrderResponseToEntity(it)
        }
    }

    fun getDetailSellerOrder(id: Int): Observable<SellerProductInterestedEntity> {
        return service.detailSellerOrder(id).mapObservable {
            DaftarJualMapper.sellerOrderMapToEntity(it)
        }
    }

    fun updateStatusOrder(
        id: Int,
        updateStatusOrder: UpdateStatusProductReq
    ): Observable<UpdateStatusProduct> {
        return service.updateStatusProduct(id, updateStatusOrder).mapObservable {
            DaftarJualMapper.updateStatusProduct(it)
        }
    }
}