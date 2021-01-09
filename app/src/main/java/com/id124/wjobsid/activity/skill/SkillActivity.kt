package com.id124.wjobsid.activity.skill

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivitySkillBinding
import com.id124.wjobsid.util.form_validate.ValidateSkill.Companion.valSkill

class SkillActivity : BaseActivityCoroutine<ActivitySkillBinding>(), View.OnClickListener {
    private lateinit var viewModel: SkillViewModel
    private var skId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_skill
        super.onCreate(savedInstanceState)

        skId = intent.getIntExtra("sk_id", 0)

        setToolbarActionBar()
        initTextWatcher()
        setDataFromIntent()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_skill -> {
                when {
                    !valSkill(bind.inputLayoutSkill, bind.etSkill) -> {}
                    else -> {
                        if (skId != 0) {
                            viewModel.serviceUpdateApi(
                                skId = skId!!,
                                skSkillName = bind.etSkill.text.toString()
                            )
                        } else {
                            viewModel.serviceCreateApi(
                                enId = sharedPref.getIdEngineer(),
                                skSkillName = bind.etSkill.text.toString()
                            )
                        }
                    }
                }
            }
            R.id.btn_delete_skill -> {
                deleteConfirmation()
            }
            R.id.ln_back -> {
                this@SkillActivity.finish()
            }
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Skill"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initTextWatcher() {
        bind.etSkill.addTextChangedListener(MyTextWatcher(bind.etSkill))
    }

    private fun setDataFromIntent() {
        if (skId != 0) {
            bind.etSkill.setText(intent.getStringExtra("sk_skill_name"))

            bind.btnDeleteSkill.visibility = View.VISIBLE
            bind.tvAddSkill.text = getString(R.string.update_skill)
            bind.btnAddSkill.text = getString(R.string.update_skill)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@SkillActivity).get(SkillViewModel::class.java)
        viewModel.setService(createApi(this@SkillActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@SkillActivity, {
            bind.btnAddSkill.visibility = View.GONE
            bind.btnDeleteSkill.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@SkillActivity, {
            if (it) {
                setResult(RESULT_OK)
                this@SkillActivity.finish()

                bind.progressBar.visibility = View.GONE
                bind.btnAddSkill.visibility = View.VISIBLE
                bind.btnDeleteSkill.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnAddSkill.visibility = View.VISIBLE
                bind.btnDeleteSkill.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@SkillActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@SkillActivity, {
            noticeToast(it)
        })
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@SkillActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this skill?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.serviceDeleteApi(
                    skId = skId!!
                )
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        dialog?.show()
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_email -> valSkill(bind.inputLayoutSkill, bind.etSkill)
            }
        }
    }
}