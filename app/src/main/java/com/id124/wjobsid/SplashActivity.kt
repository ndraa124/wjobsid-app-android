package com.id124.wjobsid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animation: Animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
        iv_logo.startAnimation(animation)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, OnboardingActivity::class.java))
            finish()
        }, 5000)
    }
}