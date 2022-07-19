package com.binar.secondhand.screen.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.usecase.notification.NotificationUseCase
import com.binar.secondhand.core.event.StateEventManager

class NotificationViewModel(private val useCase : NotificationUseCase) : ViewModel() {

    val notificationStateEvent : StateEventManager<List<Notification>> = useCase.notificationStateEventManager
    val notifType : MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    fun getNotifType():LiveData<String>{
        return notifType
    }

    fun getNotificationList(notifType : String){
        useCase.getNotificationList(notifType)
    }
}