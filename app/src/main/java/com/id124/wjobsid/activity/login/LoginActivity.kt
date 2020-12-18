package com.id124.wjobsid.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.activity.forgetpassword.ForgetPasswordVerifyActivity
import com.id124.wjobsid.activity.main.MainActivity
import com.id124.wjobsid.activity.signup.SignUpActivity
import com.id124.wjobsid.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ActivityLoginBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)
    }

    override fun onClick(v: View?) {
        val acLevel = intent.getIntExtra("level", 0)

        when (v?.id) {
            R.id.tv_forget_password -> {
                intents<ForgetPasswordVerifyActivity>(this@LoginActivity)
            }
            R.id.tv_sign_up -> {
                val intentAct = Intent(this@LoginActivity, SignUpActivity::class.java)
                intentAct.putExtra("level", acLevel)
                startActivity(intentAct)
            }
            R.id.btn_login -> {
                when {
                    bind.etEmail.text.toString().isEmpty() -> {
                        valTextLayout(input_layout_password)
                        valEditText(input_layout_email, et_email, "Please enter your email!")
                    }
                    bind.etPassword.text.toString().isEmpty() -> {
                        valTextLayout(input_layout_email)
                        valEditText(input_layout_password, et_password, "Please enter your password!")
                    }
                    else -> {
                        valTextLayout(input_layout_email)
                        valTextLayout(input_layout_password)

                        sharedPref.createAccountUser(acLevel, "Indra David Pesik", et_email.text.toString(), "1234567890")
                        intents<MainActivity>(this@LoginActivity)
                        this@LoginActivity.finish()
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