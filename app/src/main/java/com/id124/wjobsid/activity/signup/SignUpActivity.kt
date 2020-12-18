package com.id124.wjobsid.activity.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sign_up
        super.onCreate(savedInstanceState)

        if (intent.getIntExtra("level", 0) == 1) {
            bind.clCompany.visibility = VISIBLE
        } else {
            bind.clCompany.visibility = GONE
        }
    }

    override fun onClick(v: View?) {
        val acLevel = intent.getIntExtra("level", 0)

        when (v?.id) {
            R.id.btn_sign_up -> {
                Log.d("Level", acLevel.toString())
            }
            R.id.tv_login -> {
                this@SignUpActivity.finish()
            }
        }
    }
}