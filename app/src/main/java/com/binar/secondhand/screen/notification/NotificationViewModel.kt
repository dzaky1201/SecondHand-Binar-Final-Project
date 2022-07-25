package com.binar.secondhand.screen.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.secondhand.core.data.remote.notification.NotificationService
import com.binar.secondhand.core.data.remote.notification.response.NotificationResponseItem
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.usecase.notification.NotificationUseCase
import com.binar.secondhand.core.event.StateEventManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel(private val useCase : NotificationUseCase, private val service: NotificationService) : ViewModel() {

    val notificationStateEvent : StateEventManager<List<Notification>> = useCase.notificationStateEventManager
    private val listNotification:MutableLiveData<List<NotificationResponseItem>> = MutableLiveData<List<NotificationResponseItem>>()
    val notifType : MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

    fun getNotifType():LiveData<String>{
        return notifType
    }

    fun getNotificationList(notifType : String){
        useCase.getNotificationList(notifType)
    }

    fun getListNotif():LiveData<List<NotificationResponseItem>>{
        return listNotification
    }

    fun getNotifList(){
        service.getNotifList().
                enqueue(object : Callback<List<NotificationResponseItem>> {
                    override fun onResponse(
                        call: Call<List<NotificationResponseItem>>,
                        response: Response<List<NotificationResponseItem>>
                    ) {
                        listNotification.value = response.body()
                    }

                    override fun onFailure(call: Call<List<NotificationResponseItem>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
    }
}