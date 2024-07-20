package com.monetization.core.ad_units

import com.monetization.core.ad_units.core.AdUnit


interface IshfaqNativeAd : AdUnit {
    fun getTitle(): String?
    fun getDescription(): String?
    fun getCtaText(): String?
    fun getAdvertiserName(): String?
    fun destroyAd()
}
