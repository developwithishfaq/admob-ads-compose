package com.monetization.core

import android.app.Activity
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IshfaqAdsSdk(
) {
    fun initAdsSdk(context: Activity, onInitialized: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            MobileAds.initialize(context) {
                context.runOnUiThread {
                    onInitialized.invoke()
                }
            }
        }
    }
}