package com.monetization.core.ad_units

import android.app.Activity
import com.monetization.core.FullScreenAdsShowListener
import com.monetization.core.ad_units.core.AdUnit

interface IshfaqAppOpenAd : AdUnit {
    fun showAppOpen(context: Activity, callBack: FullScreenAdsShowListener)
    fun destroyAd()
}