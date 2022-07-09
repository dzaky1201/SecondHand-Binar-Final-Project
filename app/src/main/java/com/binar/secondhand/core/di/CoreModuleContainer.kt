package com.binar.secondhand.core.di

import com.binar.secondhand.core.data.repository.ProfileRepositoryImpl
import com.binar.secondhand.core.data.remote.profile.source.ProfileDataSource
import com.binar.secondhand.core.data.local.DataPreferences
import com.binar.secondhand.core.data.remote.profile.AuthService
import com.binar.secondhand.core.data.network.RetrofitProvider
import com.binar.secondhand.core.data.remote.detail.DetailService
import com.binar.secondhand.core.data.remote.detail.source.DetailDataSource
import com.binar.secondhand.core.data.remote.home.ProductService
import com.binar.secondhand.core.data.remote.home.source.HomeDataSource
import com.binar.secondhand.core.data.remote.notification.NotificationService
import com.binar.secondhand.core.data.remote.notification.source.NotificationDataSource
import com.binar.secondhand.core.data.repository.DetailRepositoryImpl
import com.binar.secondhand.core.data.repository.NotificationRepositoryImpl
import com.binar.secondhand.core.data.remote.jual.JualService
import com.binar.secondhand.core.data.remote.jual.source.JualDataSource
import com.binar.secondhand.core.data.repository.JualRepositoryImpl
import com.binar.secondhand.core.data.repository.ProductRepositoryImpl
import com.binar.secondhand.core.domain.repository.IJualRepository
import com.binar.secondhand.core.domain.repository.IProductRepository
import com.binar.secondhand.core.domain.repository.IProfileRepository
import com.binar.secondhand.core.domain.repository.iDetailRepository
import com.binar.secondhand.core.domain.repository.iNotificationRepository
import com.binar.secondhand.core.domain.usecase.detail.DetailUseCase
import com.binar.secondhand.core.domain.usecase.detail.Detailinteractor
import com.binar.secondhand.core.domain.usecase.home.ProductInteractor
import com.binar.secondhand.core.domain.usecase.home.ProductUseCase
import com.binar.secondhand.core.domain.usecase.notification.NotificationInteractor
import com.binar.secondhand.core.domain.usecase.notification.NotificationUseCase
import com.binar.secondhand.core.domain.usecase.jual.JualInteractor
import com.binar.secondhand.core.domain.usecase.jual.JualUseCase
import com.binar.secondhand.core.domain.usecase.profile.ProfileInteractor
import com.binar.secondhand.core.domain.usecase.profile.ProfileUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.create

class CoreModuleContainer : ModuleContainer() {

    private val profileServicesModule = module {
        single<AuthService> { RetrofitProvider.retrofit().create(AuthService::class.java) }
    }

    private val productServicesModule = module {
        single<ProductService> { RetrofitProvider.retrofit().create(ProductService::class.java) }
    }

    private val detailServicesModule = module {
        single<DetailService> { RetrofitProvider.retrofit().create(DetailService::class.java) }
    }


    private val notificationServicesModule = module {
        single<NotificationService> { RetrofitProvider.retrofit().create(NotificationService::class.java) }
    }
    private val jualServicesModule = module {
        single<JualService> { RetrofitProvider.retrofit().create(JualService::class.java) }
    }

    private val profileDataSourceModule = module {
        single { ProfileDataSource(get()) }
    }

    private val homeDataSourceModule = module {
        single { HomeDataSource(get()) }
    }
    private val detailDataSourceModule = module{
        single {DetailDataSource(get())}
    }

    private val notificationDataSourceModuley = module{
        single {NotificationDataSource(get())}
        
    private val jualDataSourceModule = module {
        single { JualDataSource(get()) }
    }

    private val profileRepositoryModule = module {
        factory<IProfileRepository> { ProfileRepositoryImpl(get()) }
    }

    private val homeRepositoryModule = module {
        factory<IProductRepository> { ProductRepositoryImpl(get()) }
    }

    private val detailRepositoryModule = module{
        factory<iDetailRepository> { DetailRepositoryImpl(get()) }
    }

    private val notificationRepositoryModule = module{
        factory<iNotificationRepository> { NotificationRepositoryImpl(get()) }
    private val jualRepositoryModule = module {
        factory<IJualRepository> { JualRepositoryImpl(get()) }
    }

    private val profileUseCaseModule = module {
        factory<ProfileUseCase> { ProfileInteractor(get()) }
    }

    private val homeUseCaseModule = module {
        factory<ProductUseCase> { ProductInteractor(get()) }
    }
    
    private val detailUserCaseModule = module{
        factory<DetailUseCase>{Detailinteractor(get())}
    }

    private val notificationUserCaseModule = module{
        factory<NotificationUseCase>{NotificationInteractor(get())}
        
    private val jualUseCaseModule = module {
        factory<JualUseCase> { JualInteractor(get()) }
    }

    private val preferencesModule = module {
        single { DataPreferences(androidContext()) }
    }
}