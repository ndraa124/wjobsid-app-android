package com.id124.wjobsid.activity.github

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivity
import com.id124.wjobsid.activity.github.client.ChromeClient
import com.id124.wjobsid.activity.github.client.WebClient
import com.id124.wjobsid.activity.github.client.WebViewListener
import com.id124.wjobsid.databinding.ActivityGithubBinding

class GithubActivity : BaseActivity<ActivityGithubBinding>(), WebViewListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_github
        super.onCreate(savedInstanceState)

        bind.webView.loadUrl("https://github.com/ndraa124")
        bind.webView.settings.allowContentAccess = true
        bind.webView.settings.allowFileAccess = true
        bind.webView.settings.domStorageEnabled = true
        bind.webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        bind.webView.webChromeClient = ChromeClient(this)
        bind.webView.webViewClient = WebClient(this)
    }

    override fun onPageStarted() {
        bind.progressBar.visibility = View.VISIBLE
        bind.webView.visibility = View.GONE
    }

    override fun onPageFinish() {
        bind.progressBar.visibility = View.GONE
        bind.webView.visibility = View.VISIBLE
    }

    override fun onShouldOverrideUrl(redirectUrl: String) {
        Log.d("msg", "redirect url: $redirectUrl")
    }

    override fun onProgressChange(progress: Int) {
        bind.progressBar.progress = progress
    }

    override fun onBackPressed() {
        if (bind.webView.canGoBack()) {
            bind.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}