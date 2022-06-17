package com.binar.secondhand.di

import com.binar.secondhand.akun.AkunViewModel
import com.binar.secondhand.core.di.ModuleContainer
import com.binar.secondhand.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModuleContainer : ModuleContainer() {
    private val viewModelLogin = module {
        viewModel { LoginViewModel(get()) }
    }

    private val viewModelAkun = module {
        viewModel { AkunViewModel(get()) }
    }
}