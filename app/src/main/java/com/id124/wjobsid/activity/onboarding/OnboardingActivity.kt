package com.id124.wjobsid.activity.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivity
import com.id124.wjobsid.activity.login.LoginActivity
import com.id124.wjobsid.databinding.ActivityOnboardingBinding

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_onboarding
        super.onCreate(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_engineer -> {
                val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
                intent.putExtra("level", 0)
                startActivity(intent)
                this@OnboardingActivity.finish()
            }
            R.id.btn_company -> {
                val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
                intent.putExtra("level", 1)
                startActivity(intent)
                this@OnboardingActivity.finish()
            }
        }
    }
}