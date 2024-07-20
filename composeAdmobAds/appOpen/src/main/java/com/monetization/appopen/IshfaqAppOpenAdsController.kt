package com.monetization.appopen

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.monetization.core.AdsController
import com.monetization.core.AdsLoadingStatusListener
import com.monetization.core.ad_units.IshfaqAppOpenAd
import com.monetization.core.ad_units.core.AdUnit
import com.monetization.core.commons.AdsCommons.logAds

class IshfaqAppOpenAdsController(
    private val adKey: String,
    private val adId: String,
) : AdsController {
    private var canRequestAd = true
    private var adEnabled = true
    private var currentAppOpenAd: IshfaqAppOpenAd? = null

    private var listener: AdsLoadingStatusListener? = null

    override fun setAdEnabled(enabled: Boolean) {
        adEnabled = enabled
    }

    override fun loadAd(context: Activity, callback: AdsLoadingStatusListener?) {
        logAds("loadAd function called,enabled=$adEnabled,")
        this.listener = callback
        if (adEnabled.not()) {
            listener?.onAdFailedToLoad("Ad is not enabled", -1)
            return
        }
        if (isAdRequesting()) {
            listener?.onAdFailedToLoad("Ad request is currently disabled", -1)
            return
        }
        if (isAdAvailable()) {
            listener?.onAdLoaded()
            return
        }
        canRequestAd = false
        logAds("AppOpen Ad Requested key=$adKey")

        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context, adId, request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    super.onAdLoaded(ad)

                    // If your app is going to request only one native ad at a time, set the currentNativeAd reference to null.
                    logAds("App Open Ad Loaded key=$adKey")
                    currentAppOpenAd?.destroyAd()
                    currentAppOpenAd = null
                    canRequestAd = true
                    currentAppOpenAd = AdmobAppOpenAd(ad)
                    listener?.onAdLoaded()

                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    logAds("App Open Ad Failed To Load key=$adKey")
                    canRequestAd = true
                    currentAppOpenAd = null
                    listener?.onAdFailedToLoad(error.message, error.code)
                }
            }
        )
    }

    override fun resetListener(context: Activity) {
        listener = null
    }

    override fun destroyAd(context: Activity) {
        currentAppOpenAd?.destroyAd()
        currentAppOpenAd = null
        canRequestAd = true
    }

    override fun getAdKey(): String {
        return adKey
    }

    override fun isAdAvailable(): Boolean {
        return currentAppOpenAd != null
    }

    override fun isAdRequesting(): Boolean {
        return canRequestAd.not()
    }

    override fun isAdAvailableOrRequesting(): Boolean {
        return isAdRequesting() || isAdAvailable()
    }

    override fun getAvailableAd(): AdUnit? {
        return currentAppOpenAd
    }
}