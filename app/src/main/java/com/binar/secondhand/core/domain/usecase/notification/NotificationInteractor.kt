package com.binar.secondhand.core.domain.usecase.notification

import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.repository.iDetailRepository
import com.binar.secondhand.core.domain.repository.iNotificationRepository
import com.binar.secondhand.core.event.StateEventManager

class NotificationInteractor(private val notifRepos: iNotificationRepository): NotificationUseCase {
    override val notificationStateEventManager: StateEventManager<List<Notification>> = notifRepos.notificationStateEventManager

    override fun getNotificationList() {
        notifRepos.getNotificationList()
    }

    override fun closeRepository() {
        notifRepos.close()
    }
}