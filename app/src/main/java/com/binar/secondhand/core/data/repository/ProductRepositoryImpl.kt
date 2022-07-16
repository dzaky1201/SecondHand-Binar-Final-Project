package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.home.source.HomeDataSource
import com.binar.secondhand.core.domain.model.home.Banner
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.PagingHome
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class ProductRepositoryImpl(private val homeDataSource: HomeDataSource) : IProductRepository {
    private val compositeDisposable = CompositeDisposable()
    private var _productStateEventManager: MutableStateEventManager<PagingHome<Product>> =
        MutableStateEventManager()
    override val productStateEventManager: StateEventManager<PagingHome<Product>>
        get() = _productStateEventManager

    private var _categoriesStateEventManager: MutableStateEventManager<List<Categories>> =
        MutableStateEventManager()
    override val categoriesStateEventManager: StateEventManager<List<Categories>>
        get() = _categoriesStateEventManager

    private var _searchStateEventManager: MutableStateEventManager<PagingHome<Product>> =
        MutableStateEventManager()
    override val searchStateEventManager: StateEventManager<PagingHome<Product>>
        get() = _searchStateEventManager

    private var _categoryStateEventManager: MutableStateEventManager<PagingHome<Product>> =
        MutableStateEventManager()
    override val categoryStateEventManager: StateEventManager<PagingHome<Product>>
        get() = _categoryStateEventManager

    private var _bannerStateEventManager: MutableStateEventManager<List<Banner>> =
        MutableStateEventManager()
    override val bannerStateEventManager: StateEventManager<List<Banner>>
        get() = _bannerStateEventManager

    override fun getProducts(page: Int) {
        compositeDisposable.add(
            homeDataSource.getProducts(page)
                .fetchStateEventSubscriber { productStateEvent ->
                    _productStateEventManager.post(productStateEvent)
                }
        )
    }

    override fun getCategories() {
        compositeDisposable.add(
            homeDataSource.getCategories().fetchStateEventSubscriber { categoriesStateEvent ->
                _categoriesStateEventManager.post(categoriesStateEvent)
            }
        )
    }

    override fun getBanner() {
        compositeDisposable.add(
            homeDataSource.getBannerList().fetchStateEventSubscriber { bannerList ->
                _bannerStateEventManager.post(bannerList)
            }
        )
    }

    override fun searchProduct(product: String, page: Int) {
        compositeDisposable.add(
            homeDataSource.searchProduct(product, page).fetchStateEventSubscriber { searchStateEvent ->
                _searchStateEventManager.post(searchStateEvent)
            }
        )
    }

    override fun getCategory(categoryId: Int, page: Int) {
        compositeDisposable.add(
            homeDataSource.getProductWithCategory(categoryId, page)
                .fetchStateEventSubscriber { getStateEvent ->
                    _categoryStateEventManager.post(getStateEvent)
                }
        )
    }


    override fun close() {
        _productStateEventManager.closeQuietly()
        _categoriesStateEventManager.closeQuietly()
        compositeDisposable.dispose()
    }

}