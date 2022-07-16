package com.binar.secondhand.core.data.remote.detail.source

import com.binar.secondhand.core.data.remote.detail.DetailService
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.request.WishListRequest
import com.binar.secondhand.core.data.remote.profile.request.LoginRequest
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.detail.OrdersProduct
import com.binar.secondhand.core.domain.model.detail.Wishlist
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.profile.Login
import com.binar.secondhand.core.utils.DetailMapper
import com.binar.secondhand.core.utils.ProductMapper
import com.binar.secondhand.core.utils.ProfileMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable


class DetailDataSource(private val detailService: DetailService) {

    fun getDetail(productId : Int): Observable<Detail> {
        return detailService.getDetailProduct(productId).mapObservable {
            DetailMapper.mapDetailResponseToEntity(it)
        }
    }

    fun orderProduct(request: OrderRequest): Observable<Order> {
        return detailService.orderProduct(request).mapObservable {
            DetailMapper.mapOrderResponseToEntity(it)
        }
    }

    fun checkOrderProduct():Observable<List<OrdersProduct>>{
        return detailService.checkOrderProduct().mapObservable {
            DetailMapper.mapProdResponseToEntity(it)
        }
    }

    fun addToWishlist(request: WishListRequest):Observable<Wishlist>{
        return detailService.addToWishlist(request).mapObservable {
            DetailMapper.mapWishlistResponse(it)
        }
    }
}