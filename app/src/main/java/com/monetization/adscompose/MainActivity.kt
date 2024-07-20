package com.monetization.adscompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.monetization.core.commons.AdsCommons.logAds
import com.monetization.core.commons.IshfaqConfigs
import com.monetization.core.commons.NativeTemplates
import com.monetization.interstitials.IshfaqInterstitialAdsManager
import com.monetization.nativeads.IshfaqNativeAdsManager
import com.monetization.nativeads.ShowNativeAd
import org.koin.android.ext.android.inject

val LocalInterstitialAdsManager = compositionLocalOf<IshfaqInterstitialAdsManager> {
    error("")
}
val LocalNativeAdsManager = compositionLocalOf<IshfaqNativeAdsManager> {
    error("")
}


class MainActivity : ComponentActivity() {

    private val interstitialAdsManager: IshfaqInterstitialAdsManager by inject()
    private val nativeAdsManager: IshfaqNativeAdsManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nativeAdsManager.addNewController("nativeAd", IshfaqConfigs.TestNativeId)
        setContent {

            CompositionLocalProvider(
                LocalInterstitialAdsManager provides interstitialAdsManager,
                LocalNativeAdsManager provides nativeAdsManager,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val context = LocalContext.current
                    val interAds = LocalInterstitialAdsManager.current
                    LaunchedEffect(key1 = Unit) {
                        interAds.addNewController("interAd", IshfaqConfigs.TestInterId)
                    }
                    Column {
                        ShowNativeAd(
                            enable = true,
                            context = context as Activity,
                            key = "nativeAd",
                            nativeAdsManager = nativeAdsManager,
                            adLayout = NativeTemplates.TemplateOne
                        )
                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                        )
                        Button(onClick = {
                            interAds.tryShowingInterstitialAd(
                                true,
                                "interAd",
                                context as Activity,
                                true,
                                onAdDismiss = {
                                    logAds("onAdDismiss Ad Shown=$it")
                                }
                            )
                        }) {
                            Text(text = "Show")
                        }
                    }
                }
            }
        }
    }
}