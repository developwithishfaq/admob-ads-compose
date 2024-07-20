package com.monetization.nativeads

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.shimmer.ShimmerFrameLayout
import com.monetization.core.AdsLoadingStatusListener
import com.monetization.core.ad_units.IshfaqNativeAd
import com.monetization.core.commons.AdsCommons.logAds
import com.monetization.core.commons.NativeConstants.getAdLayout
import com.monetization.core.commons.NativeTemplates
import com.monetization.nativeads.ui.IshfaqNativeView
import ir.kaaveh.sdpcompose.sdp

@androidx.compose.runtime.Composable
fun ShowNativeAd(
    enable: Boolean,
    context: Activity,
    key: String,
    nativeAdsManager: IshfaqNativeAdsManager,
    modifier: Modifier = Modifier,
    adLayout: String = NativeTemplates.TemplateTwo,
    hideShimmerOnFail: Boolean = true,
    showShimmer: Boolean = true,
) {
    var isVisible by remember {
        mutableStateOf(true)
    }
    var onAdFailed by remember {
        mutableStateOf(false)
    }
    DisposableEffect(Unit) {
        isVisible = true
        onDispose {
            isVisible = false
        }
    }
    val controller by remember {
        mutableStateOf(nativeAdsManager.getAdController(key))
    }
    if (controller == null) {
        logAds("Controller For $key is null, Please Add Controller Before The Launch Of Current Screen")
        return
    }
    var nativeAd by remember {
        mutableStateOf(controller?.getAvailableAd() as? IshfaqNativeAd)
    }
    LaunchedEffect(key1 = Unit) {
        logAds("Controller = ${controller != null}, Native Ad= ${nativeAd != null}")
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.sdp),
        shape = RoundedCornerShape(3),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
    ) {
        if (nativeAd != null && isVisible && enable) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 2.dp,
                            top = 0.dp,
                            end = 2.dp,
                            bottom = 0.dp
                        ),
                    factory = {
                        val layout = adLayout.getAdLayout(it)
                        layout
                    },
                ) { layout: View ->
                    val adView = layout.findViewById<IshfaqNativeView>(R.id.ishfaqNative)
                    nativeAdsManager.populateAd(
                        adViewLayout = adView,
                        ad = nativeAd!!,
                        onPopulated = {
                            if (isVisible) {
                                controller?.destroyAd(context)
                            }
                        }
                    )
                }
            }
        } else if (showShimmer) {
            if (onAdFailed && hideShimmerOnFail) {
                return@Card
            }
            AndroidView(
                modifier = Modifier.padding(
                    start = 2.dp,
                    top = 2.dp,
                    end = 2.dp,
                    bottom = 2.dp
                ),
                factory = {
                    val layout = LinearLayout(context)

                    val shimmerLayout =
                        LayoutInflater.from(context).inflate(R.layout.shimmer, null, false)
                    val myAdLayout = adLayout.getAdLayout(it)

                    shimmerLayout?.findViewById<ShimmerFrameLayout>(R.id.shimmerRoot)
                        ?.let { shimmer ->
                            shimmer.removeAllViews()
                            shimmer.addView(myAdLayout)
                            layout.removeAllViews()
                            layout.addView(shimmer)
                        }
                    layout
                },
            )
        }
    }

    LaunchedEffect(nativeAd, isVisible) {
        if (isVisible) {
            if (nativeAd == null) {
                controller?.loadAd(context, object : AdsLoadingStatusListener {
                    override fun onAdLoaded() {
                        nativeAd = controller?.getAvailableAd() as? IshfaqNativeAd
                        onAdFailed = false
                    }

                    override fun onAdFailedToLoad(message: String, code: Int) {
                        onAdFailed = true
                    }
                })
            }
        } else {
            controller?.resetListener(context)
        }
    }


}