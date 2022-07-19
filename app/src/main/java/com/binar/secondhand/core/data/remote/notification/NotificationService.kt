package com.binar.secondhand.core.data.remote.notification

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.notification.response.NotificationResponseItem
import com.binar.secondhand.core.data.remote.profile.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NotificationService {

    @GET("notification")
    fun getNotificationList(@Query("notification_type") notifType:String =""): SecondHandResponse<List<NotificationResponseItem>>
}