package com.binar.secondhand.core.data.remote.notification.source

import com.binar.secondhand.core.data.remote.detail.DetailService
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.notification.NotificationService
import com.binar.secondhand.core.data.remote.profile.request.LoginRequest
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.model.profile.Login
import com.binar.secondhand.core.utils.*
import io.reactivex.Observable


class NotificationDataSource(private val notifService: NotificationService) {

    fun getNotificationList(notifType : String): Observable<List<Notification>> {
        return notifService.getNotificationList(notifType).mapObservable {
            NotificationMapper.mapNotifResponseToEntity(it)
        }
    }

}