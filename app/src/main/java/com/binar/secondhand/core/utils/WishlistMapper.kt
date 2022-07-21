package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.remote.detail.response.DetailResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrderResponseItem
import com.binar.secondhand.core.data.remote.detail.response.OrdersResponseItem
import com.binar.secondhand.core.data.remote.detail.response.WishListResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.data.remote.profile.response.UserResponse
import com.binar.secondhand.core.data.remote.wishlist.response.ListWishlistResponseItem
import com.binar.secondhand.core.domain.model.detail.Detail
import com.binar.secondhand.core.domain.model.detail.Order
import com.binar.secondhand.core.domain.model.detail.OrdersProduct
import com.binar.secondhand.core.domain.model.detail.Wishlist
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.model.profile.User
import com.binar.secondhand.core.domain.model.wishlist.ListWishlist

object WishlistMapper {

    
    fun mapWishlistResponeToEntity(wishlistResponse: List<ListWishlistResponseItem?>): List<ListWishlist> {
        //TODO produce response json to detail 
        val listWishlist = wishlistResponse.map {
            WishlistMapper.mapListWishlist(it)
        }
        return listWishlist
    }

    fun mapListWishlist(wishlistResponse: ListWishlistResponseItem?): ListWishlist {

        val product : ListWishlist.Product = ListWishlist.Product()

        product.basePrice = wishlistResponse?.product?.basePrice.orNol()
        product.createdAt = wishlistResponse?.product?.createdAt.orEmpty()
        product.description = wishlistResponse?.product?.description.toString()
        product.id = wishlistResponse?.product?.id.orNol()
        product.imageName = wishlistResponse?.product?.imageName.orEmpty()
        product.imageUrl = wishlistResponse?.product?.imageUrl.orEmpty()
        product.location = wishlistResponse?.product?.location.orEmpty()
        product.name = wishlistResponse?.product?.name!!
        product.updatedAt = wishlistResponse?.product?.updatedAt.orEmpty()
        product.userId = wishlistResponse?.product?.userId.orNol()
        product.status = wishlistResponse?.product?.status.orEmpty()

        return ListWishlist(
            wishlistResponse?.createdAt.orEmpty(),
            wishlistResponse?.id.orNol(),
            product,
            wishlistResponse?.productId.orNol(),
            wishlistResponse?.updatedAt.orEmpty(),
            wishlistResponse?.userId.orNol(),
        )
    }

}