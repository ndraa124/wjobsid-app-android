package com.id124.wjobsid.activity.github.client

interface WebViewListener {
    fun onPageStarted()
    fun onPageFinish()
    fun onShouldOverrideUrl(redirectUrl: String)
    fun onProgressChange(progress: Int)
}