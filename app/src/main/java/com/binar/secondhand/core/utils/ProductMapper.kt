package com.binar.secondhand.core.utils

import com.binar.secondhand.core.data.remote.home.response.CategoriesResponseItem
import com.binar.secondhand.core.data.remote.home.response.ProductResponseItem
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product

object ProductMapper {

     fun mapProductResponseToEntity(productResponseItem: ProductResponseItem): Product {
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

    fun mapCategoriesResponseToEntity(categoriesList: CategoriesResponseItem): Categories {

        return Categories(
            categoriesList.id.orNol(),
            categoriesList.name.orEmpty(),
            categoriesList.createdAt.orEmpty(),
            categoriesList.updatedAt.orEmpty(),
        )
    }
    fun mapCatResponseToEntity(categoriesList : List<CategoriesResponseItem>): List<Categories> {
        val categories = categoriesList.map {
            mapCategoriesResponseToEntity(it)
        }
        return categories
    }
}