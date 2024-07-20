package com.monetization.core.commons

import android.content.Context
import android.view.LayoutInflater
import android.view.View

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

}