package com.id124.wjobsid.activity.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sign_up
        super.onCreate(savedInstanceState)

        if (intent.getIntExtra("level", 0) == 1) {
            bind.clCompany.visibility = View.VISIBLE
        } else {
            bind.clCompany.visibility = View.GONE
        }

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
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
                            viewModel.serviceEngineerApi(
                                acName = bind.etName.text.toString(),
                                acEmail = bind.etEmail.text.toString(),
                                acPhone = bind.etPhoneNumber.text.toString(),
                                acPassword = bind.etPassword.text.toString()
                            )
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
                            viewModel.serviceCompanyApi(
                                acName = bind.etName.text.toString(),
                                acEmail = bind.etEmail.text.toString(),
                                acPhone = bind.etPhoneNumber.text.toString(),
                                acPassword = bind.etPassword.text.toString(),
                                cnCompany = bind.etCompany.text.toString(),
                                cnPosition = bind.etPosition.text.toString(),
                            )
                        }
                    }
                }
            }
            R.id.tv_login -> {
                this@SignUpActivity.finish()
            }
        }
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

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@SignUpActivity).get(SignUpViewModel::class.java)
        viewModel.setService(createApi(this@SignUpActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@SignUpActivity, {
            bind.btnSignUp.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@SignUpActivity, {
            if (it) {
                bind.progressBar.visibility = View.GONE
                bind.btnSignUp.visibility = View.VISIBLE

                this@SignUpActivity.finish()
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnSignUp.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@SignUpActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@SignUpActivity, {
            noticeToast(it)
        })
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
                R.id.et_phone_number -> valPhoneNumber(bind.inputLayoutPhoneNumber, bind.etPhoneNumber)
                R.id.et_password -> valPassword(bind.inputLayoutPassword, bind.etPassword)
                R.id.et_password_confirm -> valPassConf(bind.inputLayoutPasswordConfirm, bind.etPasswordConfirm, bind.etPassword)
            }
        }
    }
}