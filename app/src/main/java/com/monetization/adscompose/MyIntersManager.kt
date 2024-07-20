package com.monetization.adscompose

import com.monetization.interstitials.IshfaqInterstitialAdsManager

class MyIntersManager(
    private val interstitialAdsManager: IshfaqInterstitialAdsManager
) {

    fun showInstantAd(key: String) {
        interstitialAdsManager.getAdController(key)
    }

}