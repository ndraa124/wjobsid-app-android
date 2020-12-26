package com.id124.wjobsid.activity.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivitySignUpBinding
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valCompany
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valEmail
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valName
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPassConf
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPassword
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPhoneNumber
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPosition

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
                        !valName(bind.inputLayoutName, bind.etName) -> {}
                        !valEmail(bind.inputLayoutEmail, bind.etEmail) -> {}
                        !valPhoneNumber(bind.inputLayoutPhoneNumber, bind.etPhoneNumber) -> {}
                        !valPassword(bind.inputLayoutPassword, bind.etPassword) -> {}
                        !valPassConf(bind.inputLayoutPasswordConfirm, bind.etPasswordConfirm, bind.etPassword) -> {}
                        else -> {
                            signUpAccount()
                        }
                    }
                } else {
                    when {
                        !valName(bind.inputLayoutName, bind.etName) -> {}
                        !valEmail(bind.inputLayoutEmail, bind.etEmail) -> {}
                        !valCompany(bind.inputLayoutCompany, bind.etCompany) -> {}
                        !valPosition(bind.inputLayoutPosition, bind.etPosition) -> {}
                        !valPhoneNumber(bind.inputLayoutPhoneNumber, bind.etPhoneNumber) -> {}
                        !valPassword(bind.inputLayoutPassword, bind.etPassword) -> {}
                        !valPassConf(bind.inputLayoutPasswordConfirm, bind.etPasswordConfirm, bind.etPassword) -> {}
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

    }

    inner class MyTextWatcher(private val view: View) : TextWatcher
}