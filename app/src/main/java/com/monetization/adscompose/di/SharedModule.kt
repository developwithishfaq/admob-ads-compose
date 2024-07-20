package com.monetization.adscompose.di

import com.monetization.interstitials.IshfaqInterstitialAdsManager
import com.monetization.nativeads.IshfaqNativeAdsManager
import org.koin.dsl.module

val modules = module {
    single {
        IshfaqInterstitialAdsManager()
    }
    single {
        IshfaqNativeAdsManager()
    }
}