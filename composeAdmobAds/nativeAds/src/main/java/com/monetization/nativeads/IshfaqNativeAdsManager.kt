package com.monetization.nativeads

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.monetization.core.AdsController
import com.monetization.core.AdsManager
import com.monetization.core.ad_units.core.AdUnit
import com.monetization.core.commons.NativeConstants.makeGone
import com.monetization.nativeads.ui.IshfaqNativeMediaView
import com.monetization.nativeads.ui.IshfaqNativeView

class IshfaqNativeAdsManager(
) : AdsManager {

    private val adsMap = HashMap<String, IshfaqNativeAdsController>()

    override fun getAdController(key: String): AdsController? = adsMap[key]

    override fun addNewController(
        adKey: String, adId: String
    ) {
        val controller = adsMap[adKey]
        if (controller == null) {
            adsMap[adKey] = IshfaqNativeAdsController(adId, adId)
        }
    }

    fun populateAd(adViewLayout: IshfaqNativeView?, ad: AdUnit, onPopulated: () -> Unit) {
        (ad as? AdMobNativeAd)?.nativeAd?.let { nativeAd ->
            adViewLayout?.apply {
                getNativeAdView()?.let { nativeAdView ->
                    val adIcon: ImageView? = findViewById(R.id.adIcon)
                    val adHeadLine: TextView? = findViewById(R.id.adHeadline)
                    val adBody: TextView? = findViewById(R.id.adBody)
                    val mediaView: IshfaqNativeMediaView? = findViewById(R.id.mediaView)
                    val adCtaBtn: TextView? = findViewById(R.id.adCtaBtn)
                    val mMedia = mediaView?.getNativeAdView()
                    nativeAdView.mediaView = mMedia
                    try {
                        nativeAdView.mediaView?.let { adMedia ->
                            adMedia.makeGone(nativeAd.mediaContent == null)
                            mMedia.makeGone(nativeAd.mediaContent == null)
                            if (nativeAd.mediaContent != null) {
                                adMedia.mediaContent = nativeAd.mediaContent
                            }
                        } ?: run {
                            nativeAdView.mediaView?.makeGone()
                            mMedia?.makeGone()
                        }
                    } catch (_: Exception) {
                        nativeAdView.mediaView?.makeGone()
                        mMedia?.makeGone()
                    }
                    val mIconView = nativeAdView.findViewById<ImageView>(R.id.adIcon)
                    nativeAdView.iconView = mIconView
                    nativeAdView.iconView?.let {
                        nativeAd.icon.let { icon ->
                            nativeAdView.mediaView?.makeGone(icon == null)
                            if (icon != null) {
                                (it as ImageView).setImageDrawable(icon.drawable)
                            }
                        }
                    } ?: run {
                        mIconView.makeGone()
                    }

                    nativeAdView.callToActionView = adCtaBtn
                    nativeAdView.bodyView = adBody
                    nativeAdView.iconView = adIcon
                    nativeAdView.headlineView = adHeadLine

                    if (nativeAd.headline.isNullOrEmpty()) {
                        adHeadLine?.visibility = View.GONE
                    } else {
                        adHeadLine?.visibility = View.VISIBLE
                        adHeadLine?.text = nativeAd.headline
                    }

                    if (nativeAd.body.isNullOrEmpty()) {
                        adBody?.visibility = View.GONE
                    } else {
                        adBody?.visibility = View.VISIBLE
                        adBody?.text = nativeAd.body
                    }
                    if (nativeAd.icon == null) {
                        adIcon?.visibility = View.GONE
                    } else {
                        adIcon?.setImageDrawable(nativeAd.icon!!.drawable)
                        adIcon?.visibility = View.VISIBLE
                    }
                    nativeAd.callToAction?.let { btn ->
                        adCtaBtn?.text = btn
                    }
                    nativeAdView.setNativeAd(nativeAd)
                    onPopulated.invoke()
                }
            }
        }
    }

}