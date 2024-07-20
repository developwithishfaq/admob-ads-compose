package com.monetization.core

interface AdsManager {
    fun getAdController(key: String): AdsController?
    fun addNewController(adKey: String, adId: String)
}