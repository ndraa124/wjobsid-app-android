package com.id124.wjobsid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class OnboardingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_engineer -> {
                val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
                intent.putExtra("level", 0)
                startActivity(intent)
            }
            R.id.btn_company -> {
                val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
                intent.putExtra("level", 1)
                startActivity(intent)
            }
        }
    }
}