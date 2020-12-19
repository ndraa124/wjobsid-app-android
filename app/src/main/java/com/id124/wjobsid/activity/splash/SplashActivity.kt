package com.id124.wjobsid.activity.splash

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.activity.main.MainActivity
import com.id124.wjobsid.activity.onboarding.OnboardingActivity
import com.id124.wjobsid.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_splash
        super.onCreate(savedInstanceState)

        val animation: Animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
        bind.ivLogo.startAnimation(animation)

        Handler().postDelayed({
            if (!sharedPref.getIsLogin()) {
                intents<OnboardingActivity>(this@SplashActivity)
                this@SplashActivity.finish()
            } else {
                intents<MainActivity>(this@SplashActivity)
                this@SplashActivity.finish()
            }
        }, 4000)
    }
}