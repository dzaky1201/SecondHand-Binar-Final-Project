package com.binar.secondhand.di

import com.binar.secondhand.akun.AkunViewModel
import com.binar.secondhand.core.di.ModuleContainer
import com.binar.secondhand.login.LoginViewModel
import com.binar.secondhand.update_akun.UpdateAkunViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModuleContainer : ModuleContainer() {
    private val viewModelLogin = module {
        viewModel { LoginViewModel(get()) }
    }

    private val viewModelAkun = module {
        viewModel { AkunViewModel(get()) }
    }

    private val viewModelUpdateAkun = module {
        viewModel { UpdateAkunViewModel(get()) }
    }
}