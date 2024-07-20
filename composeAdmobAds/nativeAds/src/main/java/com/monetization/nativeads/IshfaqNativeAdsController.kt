package com.monetization.nativeads

import android.app.Activity
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.monetization.core.AdsController
import com.monetization.core.AdsLoadingStatusListener
import com.monetization.core.ad_units.IshfaqNativeAd
import com.monetization.core.ad_units.core.AdUnit
import com.monetization.core.commons.AdsCommons.logAds

class IshfaqNativeAdsController(
    private val adKey: String,
    private val adId: String,
) : AdsController {
    private var canRequestAd = true
    private var adEnabled = true
    private var currentNativeAd: IshfaqNativeAd? = null

    private var listener: AdsLoadingStatusListener? = null

    override fun setAdEnabled(enabled: Boolean) {
        adEnabled = enabled
    }

    override fun loadAd(context: Activity, callback: AdsLoadingStatusListener?) {
        logAds("Native loadAd function called,enabled=$adEnabled,")
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
        logAds("Native Ad Requested key=$adKey")
        val adLoader = AdLoader.Builder(context, adId)
            .forNativeAd { nativeAd: NativeAd ->
                // If your app is going to request only one native ad at a time, set the currentNativeAd reference to null.
                logAds("Native Ad Loaded key=$adKey")
                currentNativeAd?.let {
                    it.destroyAd()
                }
                canRequestAd = true
                currentNativeAd = AdMobNativeAd(nativeAd)
                listener?.onAdLoaded()
            }
            .withAdListener(object : com.google.android.gms.ads.AdListener() {
                override fun onAdFailedToLoad(error: com.google.android.gms.ads.LoadAdError) {
                    logAds("Native Ad Failed To Load key=$adKey")
                    canRequestAd = true
                    currentNativeAd = null
                    listener?.onAdFailedToLoad(error.message, error.code)
                }
            })
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun resetListener(context: Activity) {
        listener = null
    }

    override fun destroyAd(context: Activity) {
        currentNativeAd?.destroyAd()
        currentNativeAd = null
        canRequestAd = true
    }

    override fun getAdKey(): String {
        return adKey
    }

    override fun isAdAvailable(): Boolean {
        return currentNativeAd != null
    }

    override fun isAdRequesting(): Boolean {
        return canRequestAd.not()
    }

    override fun isAdAvailableOrRequesting(): Boolean {
        return isAdRequesting() || isAdAvailable()
    }

    override fun getAvailableAd(): AdUnit? {
        return currentNativeAd
    }


}