package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.detail.source.DetailDataSource
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.repository.iDetailRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class DetailRepositoryImpl(private val detailDataSource: DetailDataSource): iDetailRepository{
    private val compositeDisposable = CompositeDisposable()
    private var _detailStateEventManager: MutableStateEventManager<Detail> = MutableStateEventManager()
    override val detailStateEventManager: StateEventManager<Detail>
        get() = _detailStateEventManager

    override fun getProducts(productId : Int) {
        compositeDisposable.add(
            detailDataSource.getDetail(productId).fetchStateEventSubscriber { getDetailEvent ->
                _detailStateEventManager.post(getDetailEvent)
            }
        )
    }

    override fun close() {
        _detailStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }
}