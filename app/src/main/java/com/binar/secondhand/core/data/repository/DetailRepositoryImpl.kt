package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.source.DetailDataSource
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.detail.OrdersProduct
import com.binar.secondhand.core.domain.repository.iDetailRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class DetailRepositoryImpl(private val detailDataSource: DetailDataSource): iDetailRepository{
    private val compositeDisposable = CompositeDisposable()

    private var _checkOrdersProductStateEventManager:MutableStateEventManager<List<OrdersProduct>> = MutableStateEventManager()
    override val checkOrdersStateEventManager: StateEventManager<List<OrdersProduct>>
        get() = _checkOrdersProductStateEventManager

    private var _detailStateEventManager: MutableStateEventManager<Detail> = MutableStateEventManager()
    override val detailStateEventManager: StateEventManager<Detail>
        get() = _detailStateEventManager

    private var _orderStateEventManager: MutableStateEventManager<Order> = MutableStateEventManager()
    override val orderStateEventManager: StateEventManager<Order>
        get() = _orderStateEventManager

    override fun getProducts(productId : Int) {
        compositeDisposable.add(
            detailDataSource.getDetail(productId).fetchStateEventSubscriber { getDetailEvent ->
                _detailStateEventManager.post(getDetailEvent)
            }
        )
    }

    override fun orderProduct(request: OrderRequest) {
        compositeDisposable.add(
            detailDataSource.orderProduct(request).fetchStateEventSubscriber { order ->
                _orderStateEventManager.post(order)
            }
        )
    }

    override fun checkOrdersProduct() {
        compositeDisposable.add(
            detailDataSource.checkOrderProduct().fetchStateEventSubscriber { check ->
                _checkOrdersProductStateEventManager.post(check)
            }
        )
    }
    override fun close() {
        _detailStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }
}