package com.id124.wjobsid.activity.skill

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivitySkillBinding
import com.id124.wjobsid.util.form_validate.ValidateSkill.Companion.valSkill

class SkillActivity : BaseActivityCoroutine<ActivitySkillBinding>(), View.OnClickListener {
    private lateinit var viewModel: SkillViewModel
    private lateinit var actionMenu: Menu
    private var skId: Int? = 0

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
            R.id.ln_back -> {
                this@SkillActivity.finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_skill_engineer, menu)
        actionMenu = menu!!

        if (skId != 0) {
            actionMenu.findItem(R.id.edit).isVisible = true
            actionMenu.findItem(R.id.delete).isVisible = true
            actionMenu.findItem(R.id.save).isVisible = false
            actionMenu.findItem(R.id.update).isVisible = false
            actionMenu.findItem(R.id.cancel).isVisible = false
        } else {
            actionMenu.findItem(R.id.save).isVisible = true
            actionMenu.findItem(R.id.edit).isVisible = false
            actionMenu.findItem(R.id.delete).isVisible = false
            actionMenu.findItem(R.id.update).isVisible = false
            actionMenu.findItem(R.id.cancel).isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                when {
                    !valSkill(bind.inputLayoutSkill, bind.etSkill) -> {}
                    else -> {
                        viewModel.serviceCreateApi(
                            enId = sharedPref.getIdEngineer(),
                            skSkillName = bind.etSkill.text.toString()
                        )
                    }
                }
                return true
            }
            R.id.edit -> {
                editMode()
                actionMenu.findItem(R.id.edit).isVisible = false
                actionMenu.findItem(R.id.delete).isVisible = false
                actionMenu.findItem(R.id.save).isVisible = false
                actionMenu.findItem(R.id.update).isVisible = true
                actionMenu.findItem(R.id.cancel).isVisible = true
                return true
            }
            R.id.update -> {
                when {
                    !valSkill(bind.inputLayoutSkill, bind.etSkill) -> {}
                    else -> {
                        viewModel.serviceUpdateApi(
                            skId = skId!!,
                            skSkillName = bind.etSkill.text.toString()
                        )
                    }
                }
                return true
            }
            R.id.delete -> {
                deleteConfirmation()
                return true
            }
            R.id.cancel -> {
                readMode()
                actionMenu.findItem(R.id.edit).isVisible = true
                actionMenu.findItem(R.id.delete).isVisible = true
                actionMenu.findItem(R.id.save).isVisible = false
                actionMenu.findItem(R.id.update).isVisible = false
                actionMenu.findItem(R.id.cancel).isVisible = false
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
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
            readMode()
        }
    }

    private fun editMode() {
        bind.tvInstruction.visibility = View.VISIBLE
        bind.tvAddSkill.text = getString(R.string.update_skill)
        bind.etSkill.isFocusableInTouchMode = true
    }

    private fun readMode() {
        bind.tvInstruction.visibility = View.GONE
        bind.tvAddSkill.text = getString(R.string.detail_skill)
        bind.etSkill.isFocusableInTouchMode = false
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@SkillActivity).get(SkillViewModel::class.java)
        viewModel.setService(createApi(this@SkillActivity))
    }

    private fun subscribeLiveData() {
        onLoadingLiveData()
        onSuccessLiveData()
        onFailLiveData()
    }

    private fun onLoadingLiveData() {
        viewModel.isLoadingLiveData.observe(this@SkillActivity, {
            if (it) {
                actionMenu.findItem(R.id.loading).isVisible = true

                actionMenu.findItem(R.id.edit).isVisible = false
                actionMenu.findItem(R.id.delete).isVisible = false
                actionMenu.findItem(R.id.save).isVisible = false
                actionMenu.findItem(R.id.update).isVisible = false
                actionMenu.findItem(R.id.cancel).isVisible = false
            } else {
                if (skId != 0) {
                    actionMenu.findItem(R.id.edit).isVisible = true
                    actionMenu.findItem(R.id.delete).isVisible = true
                    actionMenu.findItem(R.id.save).isVisible = false
                    actionMenu.findItem(R.id.update).isVisible = false
                } else {
                    actionMenu.findItem(R.id.edit).isVisible = false
                    actionMenu.findItem(R.id.delete).isVisible = false
                    actionMenu.findItem(R.id.save).isVisible = true
                    actionMenu.findItem(R.id.update).isVisible = false
                }

                actionMenu.findItem(R.id.loading).isVisible = false
            }
        })
    }

    private fun onSuccessLiveData() {
        viewModel.onSuccessLiveData.observe(this@SkillActivity, {
            if (it) {
                setResult(100)
                this@SkillActivity.finish()
            }
        })

        viewModel.onMessageLiveData.observe(this@SkillActivity, {
            noticeToast(it)
        })
    }

    private fun onFailLiveData() {
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