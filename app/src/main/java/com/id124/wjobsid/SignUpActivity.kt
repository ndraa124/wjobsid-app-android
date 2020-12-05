package com.id124.wjobsid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_sign_up_content.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        if (intent.getIntExtra("level", 0) == 1) {
            cl_company.visibility = VISIBLE
        } else {
            cl_company.visibility = GONE
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