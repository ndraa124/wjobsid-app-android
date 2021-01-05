package com.id124.wjobsid.activity.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivitySignUpBinding
import com.id124.wjobsid.model.account.SignUpResponse
import com.id124.wjobsid.service.AccountApiService
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valCompany
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valEmail
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valName
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPassConf
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPassword
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPhoneNumber
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class SignUpActivity : BaseActivityCoroutine<ActivitySignUpBinding>(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sign_up
        super.onCreate(savedInstanceState)

        if (intent.getIntExtra("level", 0) == 1) {
            bind.clCompany.visibility = VISIBLE
        } else {
            bind.clCompany.visibility = GONE
        }

        initTextWatcher()
    }

    private fun initTextWatcher() {
        bind.etName.addTextChangedListener(MyTextWatcher(bind.etName))
        bind.etEmail.addTextChangedListener(MyTextWatcher(bind.etEmail))

        if (intent.getIntExtra("level", 0) == 1) {
            bind.etCompany.addTextChangedListener(MyTextWatcher(bind.etCompany))
            bind.etPosition.addTextChangedListener(MyTextWatcher(bind.etPosition))
        }

        bind.etPhoneNumber.addTextChangedListener(MyTextWatcher(bind.etPhoneNumber))
        bind.etPassword.addTextChangedListener(MyTextWatcher(bind.etPassword))
        bind.etPasswordConfirm.addTextChangedListener(MyTextWatcher(bind.etPasswordConfirm))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sign_up -> {
                if (intent.getIntExtra("level", 0) == 0) {
                    when {
                        !valName(bind.inputLayoutName, bind.etName) -> {
                        }
                        !valEmail(bind.inputLayoutEmail, bind.etEmail) -> {
                        }
                        !valPhoneNumber(bind.inputLayoutPhoneNumber, bind.etPhoneNumber) -> {
                        }
                        !valPassword(bind.inputLayoutPassword, bind.etPassword) -> {
                        }
                        !valPassConf(
                            bind.inputLayoutPasswordConfirm,
                            bind.etPasswordConfirm,
                            bind.etPassword
                        ) -> {
                        }
                        else -> {
                            signUpAccount()
                        }
                    }
                } else {
                    when {
                        !valName(bind.inputLayoutName, bind.etName) -> {
                        }
                        !valEmail(bind.inputLayoutEmail, bind.etEmail) -> {
                        }
                        !valCompany(bind.inputLayoutCompany, bind.etCompany) -> {
                        }
                        !valPosition(bind.inputLayoutPosition, bind.etPosition) -> {
                        }
                        !valPhoneNumber(bind.inputLayoutPhoneNumber, bind.etPhoneNumber) -> {
                        }
                        !valPassword(bind.inputLayoutPassword, bind.etPassword) -> {
                        }
                        !valPassConf(
                            bind.inputLayoutPasswordConfirm,
                            bind.etPasswordConfirm,
                            bind.etPassword
                        ) -> {
                        }
                        else -> {
                            signUpAccount()
                        }
                    }
                }
            }
            R.id.tv_login -> {
                this@SignUpActivity.finish()
            }
        }
    }

    private fun signUpAccount() {
        val service = createApi<AccountApiService>(this@SignUpActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    if (intent.getIntExtra("level", 0) == 0) {
                        service.signUpEngineerAccount(
                            acName = bind.etName.text.toString(),
                            acEmail = bind.etEmail.text.toString(),
                            acPhone = bind.etPhoneNumber.text.toString(),
                            acPassword = bind.etPassword.text.toString(),
                            acLevel = 0
                        )
                    } else {
                        service.signUpCompanyAccount(
                            acName = bind.etName.text.toString(),
                            acEmail = bind.etEmail.text.toString(),
                            acPhone = bind.etPhoneNumber.text.toString(),
                            acPassword = bind.etPassword.text.toString(),
                            acLevel = 1,
                            cnCompany = bind.etCompany.text.toString(),
                            cnPosition = bind.etPosition.text.toString(),
                        )
                    }
                } catch (e: HttpException) {
                    runOnUiThread {
                        if (e.code() == 400) {
                            noticeToast("Email has registered!")
                        } else {
                            noticeToast("Fail to registration! Please try again later!")
                        }
                    }
                }
            }

            if (response is SignUpResponse) {
                if (response.success) {
                    Toast.makeText(this@SignUpActivity, response.message, Toast.LENGTH_SHORT).show()
                    this@SignUpActivity.finish()
                } else {
                    noticeToast(response.message)
                }
            }
        }
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_name -> valName(bind.inputLayoutName, bind.etName)
                R.id.et_email -> valEmail(bind.inputLayoutEmail, bind.etEmail)
                R.id.et_company -> valCompany(bind.inputLayoutCompany, bind.etCompany)
                R.id.et_position -> valPosition(bind.inputLayoutPosition, bind.etPosition)
                R.id.et_phone_number -> valPhoneNumber(
                    bind.inputLayoutPhoneNumber,
                    bind.etPhoneNumber
                )
                R.id.et_password -> valPassword(bind.inputLayoutPassword, bind.etPassword)
                R.id.et_password_confirm -> valPassConf(
                    bind.inputLayoutPasswordConfirm,
                    bind.etPasswordConfirm,
                    bind.etPassword
                )
            }
        }
    }
}