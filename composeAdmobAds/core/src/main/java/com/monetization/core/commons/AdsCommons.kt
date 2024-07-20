package com.monetization.core.commons

import android.app.Activity
import android.util.Log

object AdsCommons {

    fun logAds(message: String, isError: Boolean = false) {
        if (isError) {
            Log.e("adsPlugin", "Ads: $message")
        } else {
            Log.d("adsPlugin", "Ads: $message")
        }
    }

    fun Activity.getGoodName(): String {
        return localClassName.substringAfterLast(".")
    }
}