package com.monetization.core.commons

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAdOptions

object NativeTemplates {
    const val TemplateOne = "native_template_one"
    const val TemplateTwo = "native_template_two"
}

object IshfaqConfigs {
    const val TestNativeId = "ca-app-pub-3940256099942544/2247696110"
    const val TestBannerId = "ca-app-pub-3940256099942544/9214589741"
    const val TestAppOpenId = "ca-app-pub-3940256099942544/9257395921"
    const val TestInterId = "ca-app-pub-3940256099942544/1033173712"
}

object NativeConstants {
    fun Context.getLayoutId(layoutName: String): Int {
        return resources.getIdentifier(layoutName, "layout", packageName)
    }

    fun String.inflateLayoutByName(context: Context): View? {
        val layoutId = context.getLayoutId(this)
        return if (layoutId != 0) {
            LayoutInflater.from(context).inflate(layoutId, null)
        } else {
            null
        }
    }

    fun View?.makeGone(value: Boolean = true) {
        this?.visibility = if (value) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    fun View.makeVisible(value: Boolean = true) {
        visibility = if (value) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }


    fun getNativeAdOptions(context: Activity): NativeAdOptions {
        val builder = NativeAdOptions.Builder()
        if (context.window.decorView.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
            builder.setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_LEFT)
        }
        return builder.setVideoOptions(
            VideoOptions.Builder().setStartMuted(true).build()
        ).build()
    }


    fun String.getAdLayout(context: Context): View {
        try {
            val resourceId = context.resources.getIdentifier(this, "layout", context.packageName)
            return LayoutInflater.from(context).inflate(resourceId, null, false)
        } catch (_: Exception) {
            val resourceId = context.resources.getIdentifier(
                this,
                NativeTemplates.TemplateTwo,
                context.packageName
            )
            return LayoutInflater.from(context).inflate(resourceId, null, false)
        }
    }

}