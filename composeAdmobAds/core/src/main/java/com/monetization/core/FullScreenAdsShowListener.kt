package com.monetization.core

interface FullScreenAdsShowListener {
    fun onAdDismiss() {}
    fun onAdShown() {}
    fun onAdShownFailed() {}
    fun onAdClick() {}
}