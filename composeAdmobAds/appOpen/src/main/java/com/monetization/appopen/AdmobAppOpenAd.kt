package com.monetization.appopen

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import com.monetization.core.FullScreenAdsShowListener
import com.monetization.core.ad_units.IshfaqAppOpenAd
import com.monetization.core.ad_units.core.AdType

class AdmobAppOpenAd(
    private val appOpenAd: AppOpenAd
) : IshfaqAppOpenAd {
    override fun showAppOpen(context: Activity, callBack: FullScreenAdsShowListener) {
        appOpenAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                callBack.onAdShownFailed()
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()

                callBack.onAdShown()
            }

            override fun onAdClicked() {
                super.onAdClicked()
                callBack.onAdClick()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                callBack.onAdDismiss()
            }
        }
        appOpenAd.show(context)
    }

    override fun destroyAd() {
    }

    override fun getAdType(): AdType {
        return AdType.AppOpen
    }

}