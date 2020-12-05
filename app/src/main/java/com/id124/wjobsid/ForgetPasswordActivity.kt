package com.id124.wjobsid

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ForgetPasswordActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
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