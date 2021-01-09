package com.id124.wjobsid.activity.forget_password.check_email

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.forget_password.reset_password.ResetPasswordActivity
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityVerifyEmailBinding
import com.id124.wjobsid.util.form_validate.ValidateAccount.Companion.valEmail

class CheckEmailActivity : BaseActivityCoroutine<ActivityVerifyEmailBinding>(), View.OnClickListener {
    private lateinit var viewModel: CheckEmailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_verify_email
        super.onCreate(savedInstanceState)

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next -> {
                when {
                    !valEmail(bind.inputLayoutEmail, bind.etEmail) -> {}
                    else -> {
                        viewModel.serviceApi(
                            email = bind.etEmail.text.toString()
                        )
                    }
                }
            }
            R.id.ln_back -> {
                this@CheckEmailActivity.finish()
            }
        }
    }

    private fun initTextWatcher() {
        bind.etEmail.addTextChangedListener(MyTextWatcher(bind.etEmail))
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@CheckEmailActivity).get(CheckEmailViewModel::class.java)
        viewModel.setService(createApi(this@CheckEmailActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@CheckEmailActivity, {
            bind.btnNext.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@CheckEmailActivity, {
            if (it) {
                bind.progressBar.visibility = View.GONE
                bind.btnNext.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnNext.visibility = View.VISIBLE
            }
        })

        viewModel.onSetAccountId.observe(this@CheckEmailActivity, {
            val intent = Intent(this@CheckEmailActivity, ResetPasswordActivity::class.java)
            intent.putExtra("ac_id", it)
            startActivity(intent)
            this@CheckEmailActivity.finish()
        })

        viewModel.onFailLiveData.observe(this@CheckEmailActivity, {
            noticeToast(it)
        })
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_email -> valEmail(bind.inputLayoutEmail, bind.etEmail)
            }
        }
    }
}