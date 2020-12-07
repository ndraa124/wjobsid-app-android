package com.id124.wjobsid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.id124.wjobsid.R
import com.id124.wjobsid.helper.SharedPreference
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
                when {
                    et_email.text.toString().isEmpty() -> {
                        valTextLayout(input_layout_password)
                        valEditText(input_layout_email, et_email, "Please enter your email!")
                    }
                    et_password.text.toString().isEmpty() -> {
                        valTextLayout(input_layout_email)
                        valEditText(input_layout_password, et_password, "Please enter your password!")
                    }
                    else -> {
                        valTextLayout(input_layout_email)
                        valTextLayout(input_layout_password)

                        sharedPreference.setLevel(acLevel)

                        Log.d("Level", acLevel.toString())
                        val intentAct = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intentAct)
                    }
                }
            }
        }
    }

    private fun valTextLayout(inputLayout: TextInputLayout) {
        inputLayout.isHelperTextEnabled = false
    }

    private fun valEditText(inputLayout: TextInputLayout, editText: EditText, hint: String) {
        val text = editText.text.toString().trim()

        if (text.isEmpty()) {
            inputLayout.isHelperTextEnabled = true
            inputLayout.helperText = hint
            editText.requestFocus()
        } else {
            inputLayout.isHelperTextEnabled = false
        }
    }
}