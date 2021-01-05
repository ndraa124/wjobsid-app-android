package com.id124.wjobsid.activity.skill

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivitySkillBinding
import com.id124.wjobsid.model.skill.SkillResponse
import com.id124.wjobsid.service.SkillApiService
import com.id124.wjobsid.util.form_validate.ValidateSkill.Companion.valSkill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SkillActivity : BaseActivityCoroutine<ActivitySkillBinding>(), View.OnClickListener {
    private var skId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_skill
        super.onCreate(savedInstanceState)

        skId = intent.getIntExtra("sk_id", 0)

        setToolbarActionBar()
        initTextWatcher()
        setDataFromIntent()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_skill -> {
                when {
                    !valSkill(bind.inputLayoutSkill, bind.etSkill) -> {}
                    else -> {
                        if (skId != 0) {
                            updateSkill()
                        } else {
                            createSkill()
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

    private fun createSkill() {
        val service = createApi<SkillApiService>(this@SkillActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.createSkill(
                        enId = sharedPref.getIdEngineer(),
                        skSkillName = bind.etSkill.text.toString()
                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                }
            }

            if (response is SkillResponse) {
                setResult(RESULT_OK)
                this@SkillActivity.finish()
            }
        }
    }

    private fun updateSkill() {
        val service = createApi<SkillApiService>(this@SkillActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateSkill(
                        skId = skId!!,
                        skSkillName = bind.etSkill.text.toString()
                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                }
            }

            if (response is SkillResponse) {
                setResult(RESULT_OK)
                this@SkillActivity.finish()
            }
        }
    }

    private fun deleteSkill() {
        val service = createApi<SkillApiService>(this@SkillActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.deleteSkill(
                        skId = skId!!
                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                }
            }

            if (response is SkillResponse) {
                setResult(RESULT_OK)
                this@SkillActivity.finish()
            }
        }
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@SkillActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this skill?")
            .setPositiveButton("OK") { _, _ ->
                deleteSkill()
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