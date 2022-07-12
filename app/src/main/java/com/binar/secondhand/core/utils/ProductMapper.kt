package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.remote.home.response.BannerResponseItem
import com.binar.secondhand.core.data.remote.home.response.CategoriesResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.data.remote.home.response.paging.BasePagingResponse
import com.binar.secondhand.core.domain.model.home.Banner
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.PagingHome
import com.binar.secondhand.core.domain.model.home.Product

object ProductMapper {

    private fun mapProductResponseToEntity(productResponseItem: ProductResponseItem): Product {
        val categoryResponse = productResponseItem.categories?.map {
            Product.Category(
                id = it.id.orNol(),
                name = it.name.orEmpty()
            )
        }
        return Product(
            productResponseItem.basePrice.orNol(),
            categoryResponse.orEmpty(),
            productResponseItem.createdAt.orEmpty(),
            productResponseItem.description.orEmpty(),
            productResponseItem.id.orNol(),
            productResponseItem.imageName.orEmpty(),
            productResponseItem.imageUrl.orEmpty(),
            productResponseItem.location.orEmpty(),
            productResponseItem.name.orEmpty(),
            productResponseItem.status.orEmpty(),
            productResponseItem.updatedAt.orEmpty(),
            productResponseItem.userId.orNol()
        )
    }

    fun mapProdResponseToEntity(productList: List<ProductResponseItem>): List<Product> {
        val product = productList.map {
            mapProductResponseToEntity(it)
        }
        return product
    }

    fun mapProdResponsePagingToEntity(productList: BasePagingResponse<ProductResponseItem>): PagingHome<Product> {
        val productListResponse = productList.data
        val product = productListResponse?.map {
            mapProductResponseToEntity(it)
        }
        return PagingHome(
            page = productList.page,
            perPage = productList.perPage,
            data = product
        )
    }

    private fun mapCategoriesResponseToEntity(categoriesList: CategoriesResponseItem): Categories {

        return Categories(
            categoriesList.id.orNol(),
            categoriesList.name.orEmpty(),
            categoriesList.createdAt.orEmpty(),
            categoriesList.updatedAt.orEmpty(),
        )
    }

    fun mapCatResponseToEntity(categoriesList: List<CategoriesResponseItem>): List<Categories> {
        val categories = categoriesList.map {
            mapCategoriesResponseToEntity(it)
        }
        return categories
    }

    fun mapBannerResponseToEntity(bannerList: List<BannerResponseItem>): List<Banner> {
        return bannerList.map {
            mapBannerToEntity(it)
        }
    }

    private fun mapBannerToEntity(bannerResponseItem: BannerResponseItem): Banner {
        return Banner(
            bannerResponseItem.id,
            bannerResponseItem.imageUrl,
            bannerResponseItem.name
        )
    }
}