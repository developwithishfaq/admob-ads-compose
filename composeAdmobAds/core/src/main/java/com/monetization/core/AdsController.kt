package com.monetization.core

import android.app.Activity
import com.monetization.core.ad_units.core.AdUnit


interface AdsController {
    fun setAdEnabled(enabled: Boolean)
    fun loadAd(context: Activity, callback: AdsLoadingStatusListener?)
    fun resetListener(context: Activity)
    fun destroyAd(context: Activity)
    fun getAdKey(): String
    fun isAdAvailable(): Boolean
    fun isAdRequesting(): Boolean
    fun isAdAvailableOrRequesting(): Boolean
    fun getAvailableAd(): AdUnit?
}