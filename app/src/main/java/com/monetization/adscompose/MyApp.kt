package com.monetization.adscompose

import android.app.Activity
import com.monetization.appopen.IshfaqBaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : IshfaqBaseApp() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(com.monetization.adscompose.di.modules)
        }
    }

    override fun onShowAppOpenAd(activity: Activity) {

    }

    override fun canShowAppOpenAd(activity: Activity): Boolean {
        return true
    }
}