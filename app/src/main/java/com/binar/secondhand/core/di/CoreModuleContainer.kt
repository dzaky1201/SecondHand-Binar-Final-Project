package com.binar.secondhand.core.di

import com.binar.secondhand.core.data.ProfileRepositoryImpl
import com.binar.secondhand.core.data.source.ProfileDataSource
import com.binar.secondhand.core.data.source.local.DataPreferences
import com.binar.secondhand.core.data.source.remote.AuthService
import com.binar.secondhand.core.data.source.remote.network.RetrofitProvider
import com.binar.secondhand.core.domain.repository.IProfileRepository
import com.binar.secondhand.core.domain.usecase.ProfileInteractor
import com.binar.secondhand.core.domain.usecase.ProfileUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class CoreModuleContainer : ModuleContainer() {

    private val webServicesModule = module {
        single<AuthService> { RetrofitProvider.retrofit().create(AuthService::class.java) }
    }

    private val dataSourceModule = module {
        single { ProfileDataSource(get()) }
    }

    private val repositoryModule = module {
        factory<IProfileRepository> { ProfileRepositoryImpl(get()) }
    }

    private val useCaseModule = module {
        factory<ProfileUseCase> { ProfileInteractor(get()) }
    }

    private val preferencesModule = module {
        single { DataPreferences(androidContext()) }
    }
}