package com.monetization.core

interface AdsLoadingStatusListener {
    fun onAdLoaded()
    fun onAdFailedToLoad(message: String, code: Int)

}