package com.monetization.appopen

import android.app.Activity
import android.app.Application
import android.os.Bundle

abstract class IshfaqBaseApp : Application(), Application.ActivityLifecycleCallbacks {

    private var isAppInPause = false
    private var currentActivity: Activity? = null

    abstract fun onShowAppOpenAd(activity: Activity)
    abstract fun canShowAppOpenAd(activity: Activity): Boolean

    override fun onCreate() {
        super.onCreate()
    }

    fun initAppOpenAds(appOpenAdsManager: IshfaqAppOpenAdsManager) {
        registerActivityLifecycleCallbacks(this)
        appOpenAdsManager.initAppOpen(object : AppOpenListener {
            override fun onShowAd() {
                currentActivity?.let {
                    if (canShowAppOpenAd(it)) {
                        onShowAppOpenAd(it)
                    }
                }
            }
        })
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        isAppInPause = false
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        isAppInPause = false
    }

    override fun onActivityPaused(activity: Activity) {
        isAppInPause = true
    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        currentActivity = null
        isAppInPause = false
    }
}