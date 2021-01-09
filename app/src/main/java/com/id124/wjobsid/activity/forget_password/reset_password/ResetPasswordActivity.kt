package com.id124.wjobsid.activity.forget_password.reset_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityResetPasswordBinding
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPassConf
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valPassword

class ResetPasswordActivity : BaseActivityCoroutine<ActivityResetPasswordBinding>(), View.OnClickListener {
    private lateinit var viewModel: ResetPasswordViewModel
    private var acId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_reset_password
        super.onCreate(savedInstanceState)
        acId = intent.getIntExtra("ac_id", 0)

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_reset_password -> {
                when {
                    !valPassword(bind.inputLayoutNewPassword, bind.etNewPassword) -> {}
                    !valPassConf(
                        bind.inputLayoutPasswordConfirm,
                        bind.etPasswordConfirm,
                        bind.etNewPassword
                    ) -> {}
                    else -> {
                        viewModel.serviceUpdate(
                            acId = acId!!,
                            acPassword = bind.etNewPassword.text.toString()
                        )
                    }
                }
            }
            R.id.ln_back -> {
                this@ResetPasswordActivity.finish()
            }
        }
    }

    private fun initTextWatcher() {
        bind.etNewPassword.addTextChangedListener(MyTextWatcher(bind.etNewPassword))
        bind.etPasswordConfirm.addTextChangedListener(MyTextWatcher(bind.etPasswordConfirm))
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ResetPasswordActivity).get(ResetPasswordViewModel::class.java)
        viewModel.setService(createApi(this@ResetPasswordActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ResetPasswordActivity, {
            bind.btnResetPassword.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@ResetPasswordActivity, {
            if (it) {
                bind.progressBar.visibility = View.GONE
                bind.btnResetPassword.visibility = View.VISIBLE

                this@ResetPasswordActivity.finish()
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnResetPassword.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@ResetPasswordActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@ResetPasswordActivity, {
            noticeToast(it)
        })
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_password -> valPassword(
                    bind.inputLayoutNewPassword,
                    bind.etNewPassword
                )
                R.id.et_password_confirm -> valPassConf(
                    bind.inputLayoutPasswordConfirm,
                    bind.etPasswordConfirm,
                    bind.etNewPassword
                )
            }
        }
    }
}