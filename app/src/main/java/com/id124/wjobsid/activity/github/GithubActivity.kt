package com.id124.wjobsid.activity.github

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.activity.github.client.ChromeClient
import com.id124.wjobsid.activity.github.client.WebClient
import com.id124.wjobsid.activity.github.client.WebViewListener
import com.id124.wjobsid.databinding.ActivityGithubBinding

class GithubActivity : BaseActivity<ActivityGithubBinding>(), WebViewListener {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_github
        super.onCreate(savedInstanceState)

        bind.webView.loadUrl("https://github.com/ndraa124")
        bind.webView.settings.javaScriptEnabled = true
        bind.webView.settings.builtInZoomControls = true
        bind.webView.settings.allowContentAccess = true
        bind.webView.settings.databaseEnabled = true
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


}