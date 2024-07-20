package com.monetization.core.ad_units

import android.app.Activity
import com.monetization.core.FullScreenAdsShowListener
import com.monetization.core.ad_units.core.AdUnit

interface IshfaqInterstitialAd : AdUnit {
    fun showInter(context: Activity, callBack: FullScreenAdsShowListener)
    fun destroyAd()
}