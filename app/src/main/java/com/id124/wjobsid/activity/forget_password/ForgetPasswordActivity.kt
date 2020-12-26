package com.id124.wjobsid.activity.forget_password

import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivity
import com.id124.wjobsid.databinding.ActivityForgetPasswordBinding

class ForgetPasswordActivity : BaseActivity<ActivityForgetPasswordBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_forget_password
        super.onCreate(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_reset_password -> {

            }
            R.id.ln_back -> {
                this@ForgetPasswordActivity.finish()
            }
        }
    }
}