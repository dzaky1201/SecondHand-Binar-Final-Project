package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.daftar_jual.source.DaftarJualSource
import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IDaftarJualRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class DaftarJualRepositoryImpl(private val source: DaftarJualSource) : IDaftarJualRepository {
    private val compositeDisposable = CompositeDisposable()

    private var _productSellerStateEventManager: MutableStateEventManager<List<Product>> =
        MutableStateEventManager()
    override val productSellerStateEventManager: StateEventManager<List<Product>>
        get() = _productSellerStateEventManager

    override fun getSellerProducts() {
        compositeDisposable.add(
            source.getProductSeller().fetchStateEventSubscriber { productSellerStateEvent ->
                _productSellerStateEventManager.post(productSellerStateEvent)
            }
        )
    }

    private val _sellerOrderStateEventManager: MutableStateEventManager<List<SellerProductInterestedEntity>> =
        MutableStateEventManager()
    override val sellerOrderStateEventManager: StateEventManager<List<SellerProductInterestedEntity>>
        get() = _sellerOrderStateEventManager

    override fun getSellerOrder() {
        compositeDisposable.add(
            source.getSellerOrder().fetchStateEventSubscriber { sellerStateEvent ->
                _sellerOrderStateEventManager.post(sellerStateEvent)
            }
        )
    }

    private var _sellerOrderDetailStateEventManager: MutableStateEventManager<SellerProductInterestedEntity> =
        MutableStateEventManager()
    override val sellerOrderDetailStateEventManager: StateEventManager<SellerProductInterestedEntity>
        get() = _sellerOrderDetailStateEventManager

    override fun getDetailSellerOrder(id: Int) {
        compositeDisposable.add(
            source.getDetailSellerOrder(id).fetchStateEventSubscriber {
                _sellerOrderDetailStateEventManager.post(it)
            }
        )
    }

    override fun close() {
        _productSellerStateEventManager.closeQuietly()
        _sellerOrderStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }
}