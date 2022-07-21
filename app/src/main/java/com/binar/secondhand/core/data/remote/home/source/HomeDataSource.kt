package com.binar.secondhand.core.data.remote.home.source

import com.binar.secondhand.core.data.remote.home.ProductService
import com.binar.secondhand.core.domain.model.home.Banner
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.PagingHome
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.utils.ProductMapper
import com.binar.secondhand.core.utils.mapObservable
import io.reactivex.Observable

class HomeDataSource(private val productService: ProductService) {

    fun getProducts(page: Int): Observable<List<Product>> {
        return productService.getProducts(page = page).mapObservable {
            ProductMapper.mapProdResponseToEntity(it)
        }
    }

    fun getCategories(): Observable<List<Categories>> {
        return productService.getCategories().mapObservable {
            ProductMapper.mapCatResponseToEntity(it)
        }
    }

    fun searchProduct(product: String, page: Int): Observable<List<Product>> {
        return productService.searchProduct(search = product, page = page).mapObservable {
            ProductMapper.mapProdResponseToEntity(it)
        }
    }

    fun getProductWithCategory(categoryId: Int, page: Int): Observable<List<Product>> {
        return productService.getCategory(categoryId, page).mapObservable {
            ProductMapper.mapProdResponseToEntity(it)
        }
    }

    fun getBannerList(): Observable<List<Banner>> =
        productService.getBanner().mapObservable { ProductMapper.mapBannerResponseToEntity(it) }
}