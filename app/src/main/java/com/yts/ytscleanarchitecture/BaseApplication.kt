package com.yts.ytscleanarchitecture

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.yts.ytscleanarchitecture.di.module.moduleList
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(moduleList)
        }
    }
}