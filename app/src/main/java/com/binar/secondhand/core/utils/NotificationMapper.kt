package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.remote.detail.response.OrderResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.data.remote.notification.response.NotificationResponseItem
import com.binar.secondhand.core.data.remote.profile.response.UserResponse
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.notification.Notification
import com.binar.secondhand.core.domain.model.notification.Product
import com.binar.secondhand.core.domain.model.profile.User

object NotificationMapper {

    
    fun mapNotificationToEntity(notifResponse: NotificationResponseItem?): Notification {

        
        //TODO produce response json to detail 

        var productModel : Product = Product()
        productModel.basePrice = notifResponse?.product!!.basePrice.orNol()
        productModel.createdAt =  notifResponse?.product!!.createdAt
        productModel.description = notifResponse?.product!!.description.orEmpty()
        productModel.id = notifResponse?.product!!.id.orNol()
        productModel.imageName = notifResponse?.product.imageName.orEmpty()
        productModel.imageUrl =  notifResponse?.product!!.imageUrl.orEmpty()
        productModel.location =  notifResponse?.product!!.location
        productModel.name = notifResponse?.product!!.name
        productModel.status =  notifResponse?.product!!.status
        productModel.updatedAt =  notifResponse?.product!!.updatedAt
        productModel.userId =  notifResponse?.product!!.userId.orNol()

        val userModel : Detail.UserModel = Detail.UserModel()
        userModel.id = notifResponse.user.id
        userModel.address = notifResponse.user.address
        userModel.fullname = notifResponse.user.fullName
        userModel.city = notifResponse.user.city
        userModel.imageUrl = notifResponse.user.imageUrl
        userModel.phoneNumber = notifResponse.user.phoneNumber
        userModel.email = notifResponse.user.email



        return Notification(
            notifResponse?.basePrice.orEmpty(),
            notifResponse?.bidPrice.orNol(),
            notifResponse?.buyerName.orEmpty(),
            notifResponse?.createdAt.orEmpty(),
            notifResponse?.id.orNol(),
            notifResponse?.imageUrl.orEmpty(),
            productModel,
            notifResponse?.productId.orNol(),
            notifResponse?.productName.orEmpty(),
            notifResponse?.read!!.or(false),
            notifResponse?.receiverId.orNol(),
            notifResponse?.sellerName.orEmpty(),
            notifResponse?.status.orEmpty(),
            notifResponse?.transactionDate.orEmpty(),
            notifResponse?.updatedAt.orEmpty(),
            userModel
        )
    }


    fun mapNotifResponseToEntity(notifList: List<NotificationResponseItem>): List<Notification> {
        val notification = notifList.map {
            mapNotificationToEntity(it)
        }
        return notification
    }
}