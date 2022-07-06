package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.home.source.HomeDataSource
import com.binar.secondhand.core.data.remote.jual.request.SellerProductRequest
import com.binar.secondhand.core.data.remote.jual.source.JualDataSource
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.domain.repository.IJualRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly
import java.io.File

class JualRepositoryImpl(private val jualDataSource: JualDataSource): IJualRepository {
    private val compositeDisposable = CompositeDisposable()

    private var _productPostStateEventManager : MutableStateEventManager<SellerProduct> = MutableStateEventManager()
    override val productPostStateEventManager: StateEventManager<SellerProduct>
        get() = _productPostStateEventManager

    override fun postProduct(sellerProductRequest: SellerProductRequest, file: File) {
        compositeDisposable.add(
            jualDataSource.postProduct(sellerProductRequest, file).fetchStateEventSubscriber { getStateEvent ->
                _productPostStateEventManager.post(getStateEvent)
            }
        )
    }

    override fun close() {
        _productPostStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }
}