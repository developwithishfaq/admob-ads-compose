package com.monetization.nativeads

import com.google.android.gms.ads.nativead.NativeAd
import com.monetization.core.ad_units.IshfaqNativeAd
import com.monetization.core.ad_units.core.AdType

class AdMobNativeAd(val nativeAd: NativeAd) : IshfaqNativeAd {
    override fun getTitle(): String? {
        return nativeAd.headline
    }

    override fun getDescription(): String? {
        return nativeAd.body
    }

    override fun getCtaText(): String? {
        return nativeAd.callToAction
    }

    override fun getAdvertiserName(): String? {
        return nativeAd.advertiser
    }

    override fun destroyAd() {

    }


    override fun getAdType(): AdType {
        return AdType.NATIVE
    }
}