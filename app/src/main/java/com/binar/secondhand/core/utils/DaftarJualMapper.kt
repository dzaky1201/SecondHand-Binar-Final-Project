package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.remote.daftar_jual.response.SellerProductInterestedItem
import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity

object DaftarJualMapper {

    fun sellerOrderResponseToEntity(sellerOrder: List<SellerProductInterestedItem>): List<SellerProductInterestedEntity> {
        val sellerOrderEntity = sellerOrder.map {
            sellerOrderMapToEntity(it)
        }
        return sellerOrderEntity
    }

    fun sellerOrderMapToEntity(sellerProductInterestedItem: SellerProductInterestedItem): SellerProductInterestedEntity {
        return SellerProductInterestedEntity(
            sellerProductInterestedItem.id,
            sellerProductInterestedItem.buyerId,
            sellerProductInterestedItem.buyerId.orNol(),
            sellerProductInterestedItem.imageProduct,
            sellerProductInterestedItem.productName,
            sellerProductInterestedItem.transactionDate,
            sellerProductInterestedItem.price,
            sellerProductInterestedItem.basePrice,
            SellerProductInterestedEntity.UserBuyer(
                sellerProductInterestedItem.user?.address,
                sellerProductInterestedItem.user?.city,
                sellerProductInterestedItem.user?.email,
                sellerProductInterestedItem.user?.fullName,
                sellerProductInterestedItem.user?.id,
                sellerProductInterestedItem.user?.phoneNumber,
                sellerProductInterestedItem.user?.imageUrl.orEmpty()
            )
        )
    }
}