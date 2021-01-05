package com.id124.wjobsid.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.forget_password.ForgetPasswordVerifyActivity
import com.id124.wjobsid.activity.main.MainActivity
import com.id124.wjobsid.activity.onboarding.OnboardingActivity
import com.id124.wjobsid.activity.signup.SignUpActivity
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityLoginBinding
import com.id124.wjobsid.model.account.LoginResponse
import com.id124.wjobsid.service.AccountApiService
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valEmail
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPassword
import kotlinx.coroutines.*
import retrofit2.HttpException

class LoginActivity : BaseActivityCoroutine<ActivityLoginBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)

        initTextWatcher()
    }

    private fun initTextWatcher() {
        bind.etEmail.addTextChangedListener(MyTextWatcher(bind.etEmail))
        bind.etPassword.addTextChangedListener(MyTextWatcher(bind.etPassword))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_forget_password -> {
                intents<ForgetPasswordVerifyActivity>(this@LoginActivity)
            }
            R.id.tv_sign_up -> {
                val intentAct = Intent(this@LoginActivity, SignUpActivity::class.java)
                intentAct.putExtra("level", intent.getIntExtra("level", 0))
                startActivity(intentAct)
            }
            R.id.btn_login -> {
                when {
                    !valEmail(bind.inputLayoutEmail, bind.etEmail) -> {}
                    !valPassword(bind.inputLayoutPassword, bind.etPassword) -> {}
                    else -> {
                        loginAccount()
                    }
                }
            }
        }
    }

    private fun loginAccount() {
        val service = createApi<AccountApiService>(this@LoginActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.loginAccount(
                        email = bind.etEmail.text.toString(),
                        password = bind.etPassword.text.toString()
                    )
                } catch (e: HttpException) {
                    runOnUiThread {
                        when {
                            e.code() == 404 -> {
                                noticeToast("Account not registered")
                            }
                            e.code() == 400 -> {
                                noticeToast("Password is invalid!")
                            }
                            else -> {
                                noticeToast("Login is fail! Please try again later!")
                            }
                        }
                    }
                }
            }

            if (response is LoginResponse) {
                if (response.success) {
                    val data = response.data
                    val id: Int?

                    id = if (data.acLevel == 0) {
                        data.enId
                    } else {
                        data.cnId
                    }

                    sharedPref.createAccountUser(
                        id = id!!,
                        acId = data.acId,
                        acLevel = data.acLevel,
                        acName = data.acName,
                        acEmail = data.acEmail,
                        token = data.token
                    )

                    intents<MainActivity>(this@LoginActivity)
                    this@LoginActivity.finish()
                } else {
                    noticeToast(response.message)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        intents<OnboardingActivity>(this@LoginActivity)
        this@LoginActivity.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_email -> valEmail(bind.inputLayoutEmail, bind.etEmail)
                R.id.et_password -> valPassword(bind.inputLayoutPassword, bind.etPassword)
            }
        }
    }
}