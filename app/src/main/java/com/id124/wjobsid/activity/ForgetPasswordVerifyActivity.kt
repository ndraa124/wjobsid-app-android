package com.id124.wjobsid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R

class ForgetPasswordVerifyActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password_verify)
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