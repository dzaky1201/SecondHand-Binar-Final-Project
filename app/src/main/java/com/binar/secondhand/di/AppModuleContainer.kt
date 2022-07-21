package com.binar.secondhand.di

import com.binar.secondhand.core.di.ModuleContainer
import com.binar.secondhand.screen.akun.AkunViewModel
import com.binar.secondhand.screen.daftar_jual.DaftarJualViewModel
import com.binar.secondhand.screen.daftar_jual.screen.diminati.DiminatiViewModel
import com.binar.secondhand.screen.daftar_jual.SellerProducViewModel
import com.binar.secondhand.screen.daftar_jual.TerjualViewModel
import com.binar.secondhand.screen.detailbuyer.DetailViewModel
import com.binar.secondhand.screen.home.HomeViewModel
import com.binar.secondhand.screen.jual.JualViewModel
import com.binar.secondhand.screen.jual.add_product.AddProductViewModel
import com.binar.secondhand.screen.login.LoginViewModel
import com.binar.secondhand.screen.notification.NotificationViewModel
import com.binar.secondhand.screen.register.RegisterViewModel
import com.binar.secondhand.screen.update_akun.UpdateAkunViewModel
import com.binar.secondhand.screen.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModuleContainer : ModuleContainer() {
    private val viewModelLogin = module {
        viewModel { LoginViewModel(get()) }
    }

    private val viewModelAkun = module {
        viewModel { AkunViewModel(get()) }
    }

    private val viewModelHome = module {
        viewModel { HomeViewModel(get()) }
    }
    private val viewModelUpdateAkun = module {
        viewModel { UpdateAkunViewModel(get()) }
    }

    private val viewModelRegister = module {
        viewModel { RegisterViewModel(get()) }
    }
    private val viewModelDetail = module {
        viewModel { DetailViewModel(get()) }
    }

    private val viewModelNotification = module {
        viewModel { NotificationViewModel(get()) }
    }

    private val viewModelJual = module {
        viewModel { JualViewModel(get(), get()) }
    }
    private val viewModelAddProduct = module {
        viewModel { AddProductViewModel(get(), get()) }
    }

    private val viewModelDaftarJual = module {
        viewModel { DaftarJualViewModel(get(), get()) }
    }

    private val viewModelSellerProduct = module {
        viewModel { SellerProducViewModel(get(), get(), get()) }
    }

    private val viewModelDiminati = module {
        viewModel { DiminatiViewModel(get(), get()) }
    }

    private val viewModelTerjual = module {
        viewModel { TerjualViewModel(get(), get()) }
    }

    private val viewModelWishlist = module {
        viewModel { WishlistViewModel(get(),get()) }
    }
}