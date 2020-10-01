package com.yts.ytscleanarchitecture

import android.app.Application
import com.yts.ytscleanarchitecture.di.module.moduleList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(moduleList)
        }
    }
}