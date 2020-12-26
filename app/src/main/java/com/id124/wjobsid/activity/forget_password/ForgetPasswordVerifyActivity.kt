package com.id124.wjobsid.activity.forget_password

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivity
import com.id124.wjobsid.databinding.ActivityForgetPasswordVerifyBinding

class ForgetPasswordVerifyActivity : BaseActivity<ActivityForgetPasswordVerifyBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_forget_password_verify
        super.onCreate(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next -> {
                val intentAct = Intent(this@ForgetPasswordVerifyActivity, ForgetPasswordActivity::class.java)
                startActivity(intentAct)
            }
            R.id.ln_back -> {
                this@ForgetPasswordVerifyActivity.finish()
            }
        }
    }
}