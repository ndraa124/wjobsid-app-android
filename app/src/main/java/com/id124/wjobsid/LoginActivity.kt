package com.id124.wjobsid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreference = SharedPreference(this@LoginActivity)
    }

    override fun onClick(v: View?) {
        val acLevel = intent.getIntExtra("level", 0)

        when (v?.id) {
            R.id.tv_forget_password -> {
                val intentAct = Intent(this@LoginActivity, ForgetPasswordVerifyActivity::class.java)
                startActivity(intentAct)
            }
            R.id.tv_sign_up -> {
                val intentAct = Intent(this@LoginActivity, SignUpActivity::class.java)
                intentAct.putExtra("level", acLevel)
                startActivity(intentAct)
            }
            R.id.btn_login -> {
                sharedPreference.setLevel(acLevel)

                Log.d("Level", acLevel.toString())
                val intentAct = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intentAct)
            }
        }
    }
}