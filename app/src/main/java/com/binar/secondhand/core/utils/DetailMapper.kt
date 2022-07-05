package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.remote.detail.response.DetailResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrderResponseItem
import com.binar.secondhand.core.data.remote.profile.response.UserResponse
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.profile.User

object DetailMapper {

    
    fun mapDetailResponseToEntity(detailResponse: DetailResponseItem?): Detail {
        val categoryResponse = detailResponse!!.categories?.map {
            Detail.Category(
                id = it.id.orNol(),
                name = it.name.orEmpty()
            )
        }
        
        //TODO produce response json to detail 

        val userModel : Detail.UserModel = Detail.UserModel()
        userModel.id = detailResponse.user.id
        userModel.address = detailResponse.user.address
        userModel.fullname = detailResponse.user.fullName
        userModel.city = detailResponse.user.city
        userModel.imageUrl = detailResponse.user.imageUrl
        userModel.phoneNumber = detailResponse.user.phoneNumber
        userModel.email = detailResponse.user.email
        
        

        return Detail(
            detailResponse?.id,
            detailResponse?.name.orEmpty(),
            detailResponse?.description.orEmpty(),
            detailResponse?.basePrice,
            detailResponse?.imageUrl.orEmpty(),
            detailResponse?.imageName.orEmpty(),
            detailResponse?.location.orEmpty(),
            detailResponse?.userId,
            detailResponse?.status.orEmpty(),
            detailResponse?.createdAt.orEmpty(),
            detailResponse?.updatedAt.orEmpty(),
            categoryResponse.orEmpty(),
            userModel
        )
    }

    fun mapUserResponseToEntity(userResponse: UserResponse?): User {
        return User(
            userResponse?.address.orEmpty(),
            userResponse?.createdAt.orEmpty(),
            userResponse?.email.orEmpty(),
            userResponse?.fullName.orEmpty(),
            userResponse?.id,
            userResponse?.imageUrl.orEmpty(),
            userResponse?.password.orEmpty(),
            userResponse?.city.orEmpty(),
            userResponse?.phoneNumber.orEmpty(),
            userResponse?.updatedAt.orEmpty()
        )
    }

    fun mapOrderResponseToEntity(orderResponse : OrderResponseItem?):Order{
        return Order(
            orderResponse?.basePrice.orNol(),
            orderResponse?.buyerId.orNol(),
            orderResponse?.createdAt.orEmpty(),
            orderResponse?.id.orNol(),
            orderResponse?.imageProduct.orEmpty(),
            orderResponse?.price.orNol(),
            orderResponse?.productId.orNol(),
            orderResponse?.productName.orEmpty(),
            orderResponse?.status.orEmpty(),
            orderResponse?.transactionDate.orEmpty(),
            orderResponse?.updatedAt.orEmpty(),

        )
    }
}