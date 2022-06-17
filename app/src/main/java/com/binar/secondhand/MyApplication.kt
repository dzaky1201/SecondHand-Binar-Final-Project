package com.binar.secondhand

import android.app.Application
import com.binar.secondhand.core.di.CoreModuleContainer
import com.binar.secondhand.di.AppModuleContainer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val coreModuleContainer = CoreModuleContainer()
        val appModuleContainer = AppModuleContainer()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                coreModuleContainer.modules() +
                        appModuleContainer.modules()
            )
        }
    }
}