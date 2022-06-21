package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.home.source.HomeDataSource
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class ProductRepositoryImpl(private val homeDataSource: HomeDataSource): IProductRepository {
    private val compositeDisposable = CompositeDisposable()
    private var _productStateEventManager: MutableStateEventManager<List<Product>> = MutableStateEventManager()
    override val productStateEventManager: StateEventManager<List<Product>>
        get() = _productStateEventManager

    override fun getProducts() {
        compositeDisposable.add(
            homeDataSource.getProducts().fetchStateEventSubscriber { productStateEvent ->
                _productStateEventManager.post(productStateEvent)
            }
        )
    }

    override fun close() {
        _productStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }

}