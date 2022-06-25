package com.binar.secondhand.core.data.repository

import com.binar.secondhand.core.data.remote.home.source.HomeDataSource
import com.binar.secondhand.core.domain.model.home.Categories
import com.binar.secondhand.core.domain.model.home.Product
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.event.MutableStateEventManager
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.core.utils.fetchStateEventSubscriber
import io.reactivex.disposables.CompositeDisposable
import okhttp3.internal.closeQuietly

class ProductRepositoryImpl(private val homeDataSource: HomeDataSource): IProductRepository {
    private val compositeDisposable = CompositeDisposable()
    private var _productStateEventManager: MutableStateEventManager<List<Product>> = MutableStateEventManager()
    override val productStateEventManager: StateEventManager<List<Product>>
        get() = _productStateEventManager

    private var _categoriesStateEventManager: MutableStateEventManager<List<Categories>> = MutableStateEventManager()
    override val categoriesStateEventManager: StateEventManager<List<Categories>>
        get() = _categoriesStateEventManager

    private var _searchStateEventManager: MutableStateEventManager<List<Product>> = MutableStateEventManager()
    override val searchStateEventManager: StateEventManager<List<Product>>
    get() = _searchStateEventManager

    private var _categoryStateEventManager : MutableStateEventManager<List<Product>> = MutableStateEventManager()
    override val categoryStateEventManager : StateEventManager<List<Product>>
    get() = _categoryStateEventManager

    override fun getProducts() {
        compositeDisposable.add(
            homeDataSource.getProducts().fetchStateEventSubscriber { productStateEvent ->
                _productStateEventManager.post(productStateEvent)
            }
        )
    }

    override fun getCategories(){
        compositeDisposable.add(
            homeDataSource.getCategories().fetchStateEventSubscriber {  categoriesStateEvent ->
                _categoriesStateEventManager.post(categoriesStateEvent)
            }
        )
    }

    override fun searchProduct(product:String) {
        compositeDisposable.add(
            homeDataSource.searchProduct(product).fetchStateEventSubscriber { searchStateEvent ->
                _searchStateEventManager.post(searchStateEvent)
            }
        )
    }
    override fun getCategory(categoryId:Int) {
        compositeDisposable.add(
            homeDataSource.getCategory(categoryId).fetchStateEventSubscriber { getStateEvent ->
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