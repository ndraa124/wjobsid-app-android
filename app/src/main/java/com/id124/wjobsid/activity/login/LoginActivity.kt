package com.id124.wjobsid.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.forget_password.check_email.CheckEmailActivity
import com.id124.wjobsid.activity.main.MainActivity
import com.id124.wjobsid.activity.onboarding.OnboardingActivity
import com.id124.wjobsid.activity.signup.SignUpActivity
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityLoginBinding
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valEmail
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPassword
import kotlinx.coroutines.*

class LoginActivity : BaseActivityCoroutine<ActivityLoginBinding>(), View.OnClickListener {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_forget_password -> {
                intents<CheckEmailActivity>(this@LoginActivity)
            }
            R.id.tv_sign_up -> {
                selectSignUpAs()
            }
            R.id.btn_login -> {
                when {
                    !valEmail(bind.inputLayoutEmail, bind.etEmail) -> {}
                    !valPassword(bind.inputLayoutPassword, bind.etPassword) -> {}
                    else -> {
                        viewModel.serviceApi(
                            email = bind.etEmail.text.toString(),
                            password = bind.etPassword.text.toString()
                        )
                    }
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

    private fun initTextWatcher() {
        bind.etEmail.addTextChangedListener(MyTextWatcher(bind.etEmail))
        bind.etPassword.addTextChangedListener(MyTextWatcher(bind.etPassword))
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@LoginActivity).get(LoginViewModel::class.java)
        viewModel.setService(createApi(this@LoginActivity))
        viewModel.setSharedPref(sharedPref)
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@LoginActivity, {
            bind.btnLogin.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@LoginActivity, {
            if (it) {
                bind.progressBar.visibility = View.GONE
                bind.btnLogin.visibility = View.VISIBLE

                intents<MainActivity>(this@LoginActivity)
                this@LoginActivity.finish()
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnLogin.visibility = View.VISIBLE
            }
        })

        viewModel.onFailLiveData.observe(this@LoginActivity, {
            noticeToast(it)
        })
    }

    private fun selectSignUpAs() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
        builder.setTitle("Sign up as?")

        val user = arrayOf("Engineer (Worker)", "Company (Recruiter)")
        builder.setItems(user) { _, which ->
            when (which) {
                0 -> {
                    val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                    intent.putExtra("level", 0)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                    intent.putExtra("level", 1)
                    startActivity(intent)
                }
            }
        }.show()
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