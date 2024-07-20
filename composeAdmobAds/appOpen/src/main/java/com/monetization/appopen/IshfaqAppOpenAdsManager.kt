package com.monetization.appopen

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.monetization.core.AdsController
import com.monetization.core.AdsManager

class IshfaqAppOpenAdsManager : AdsManager, DefaultLifecycleObserver {

    private val adsMap = HashMap<String, IshfaqAppOpenAdsController>()
    private var listener: AppOpenListener? = null

    override fun getAdController(key: String): AdsController? {
        return adsMap[key]
    }

    override fun addNewController(adKey: String, adId: String) {
        val controller = adsMap[adKey]
        if (controller == null) {
            adsMap[adKey] = IshfaqAppOpenAdsController(adId, adId)
        }
    }

    fun initAppOpen(callBack: AppOpenListener) {
        listener = callBack
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        listener?.onShowAd()
    }
}