package com.crafsed.sas

import android.app.Application
import com.crafsed.sas.vm.LoginViewModel
import com.crafsed.sas.vm.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

class KoinApp: Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@KoinApp)
            androidFileProperties()
            modules(appModule)
        }
    }
}

val appModule = module {
    viewModel { LoginViewModel() }
    viewModel { MainViewModel() }
}