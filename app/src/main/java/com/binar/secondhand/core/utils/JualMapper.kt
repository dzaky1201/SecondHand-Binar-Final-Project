package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.remote.jual.response.SellerProductResponse
import com.binar.secondhand.core.data.remote.profile.response.LoginResponse
import com.binar.secondhand.core.domain.model.jual.SellerProduct
import com.binar.secondhand.core.domain.model.profile.Login

object JualMapper {
    fun mapSellerProductResponseToEntity(sellerProductResponse: SellerProductResponse?): SellerProduct {
        return SellerProduct(
           sellerProductResponse?.basePrice,
            sellerProductResponse?.createdAt,
            sellerProductResponse?.description,
            sellerProductResponse?.id,
            sellerProductResponse?.imageName,
            sellerProductResponse?.imageUrl,
            sellerProductResponse?.location,
            sellerProductResponse?.name,
            sellerProductResponse?.status,
            sellerProductResponse?.updatedAt,
            sellerProductResponse?.userId
        )
    }
}