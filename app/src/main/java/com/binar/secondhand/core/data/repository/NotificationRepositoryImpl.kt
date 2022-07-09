package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.source.DetailDataSource
import com.binar.secondhand.core.data.remote.notification.source.NotificationDataSource
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.repository.iDetailRepository
import com.binar.secondhand.core.domain.repository.iNotificationRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class NotificationRepositoryImpl(private val notifDataSource: NotificationDataSource): iNotificationRepository{
    private val compositeDisposable = CompositeDisposable()
    private var _notificationStateEventManager: MutableStateEventManager<List<Notification>> = MutableStateEventManager()
    override val notificationStateEventManager: StateEventManager<List<Notification>>
        get() = _notificationStateEventManager

    override fun getNotificationList() {
        compositeDisposable.add(
            notifDataSource.getNotificationList().fetchStateEventSubscriber { getNotifList ->
                _notificationStateEventManager.post(getNotifList)
            }
        )
    }
    override fun close() {
        _notificationStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }
}