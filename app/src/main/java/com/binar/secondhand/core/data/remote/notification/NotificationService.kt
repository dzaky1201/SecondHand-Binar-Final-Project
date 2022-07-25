package com.binar.secondhand.core.data.remote.notification

import com.binar.secondhand.core.data.network.SecondHandResponse
import com.binar.secondhand.core.data.remote.notification.response.NotificationResponseItem
import com.binar.secondhand.core.data.remote.profile.response.UserResponse
import com.binar.secondhand.core.domain.model.daftar_jual.DeleteResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationService {

    @GET("notification")
    fun getNotificationList(@Query("notification_type") notifType:String =""): SecondHandResponse<List<NotificationResponseItem>>

    @GET("notification")
    fun getNotifList(): Call<List<NotificationResponseItem>>
}