package com.binar.secondhand.di

import com.binar.secondhand.core.di.ModuleContainer
import com.binar.secondhand.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModuleContainer : ModuleContainer() {
    private val viewModel = module {
        viewModel { LoginViewModel(get()) }
    }
}