package com.id124.wjobsid.activity.github.client

import android.webkit.WebChromeClient
import android.webkit.WebView

class ChromeClient(private var listener: WebViewListener) : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        listener.onProgressChange(newProgress)
    }
}