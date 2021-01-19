package com.id124.wjobsid.activity.hire

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.ViewModelProvider
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.hire.adapter.ProjectAdapter
import com.id124.wjobsid.activity.main.fragment.profile.engineer.ProfileEngineerFragment
import com.id124.wjobsid.activity.project.ProjectActivity
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityHireBinding
import com.id124.wjobsid.model.project.ProjectModel
import com.id124.wjobsid.util.form_validate.ValidateHire.Companion.valMessage
import com.id124.wjobsid.util.form_validate.ValidateHire.Companion.valPrice

class HireActivity : BaseActivityCoroutine<ActivityHireBinding>(), View.OnClickListener, HireProjectContract.View {
    private var presenter: HireProjectPresenter? = null
    private lateinit var viewModel: HireViewModel
    private var pjId: Int? = 0
    private var enId: Int? = 0

    companion object {
        const val INTENT_ADD = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_hire
        super.onCreate(savedInstanceState)
        presenter = HireProjectPresenter(createApi(this@HireActivity))

        enId = intent.getIntExtra("en_id", 0)

        setToolbarActionBar()
        initTextWatcher()
        setProjectAdapter()

        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_process_hire -> {
                when {
                    pjId == 0 -> {
                        noticeToast("Project is empty! Please add project first.")
                        intentsResults<ProjectActivity>(this@HireActivity, INTENT_ADD)
                    }
                    !valPrice(bind.inputLayoutPrice, bind.etPrice) -> {}
                    !valMessage(bind.inputLayoutMessage, bind.etMessage) -> {}
                    else -> {
                        viewModel.serviceCreateApi(
                            enId = enId!!,
                            pjId = pjId!!,
                            hrPrice = bind.etPrice.text.toString().toLong(),
                            hrMessage = bind.etMessage.text.toString()
                        )
                    }
                }
            }
            R.id.ln_back -> {
                this@HireActivity.finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ProfileEngineerFragment.INTENT_ADD && resultCode == Activity.RESULT_OK) {
            presenter?.callService(sharedPref.getIdCompany())
        }
    }

    override fun onResultSuccess(list: List<ProjectModel>) {
        (bind.spProject.adapter as ProjectAdapter).addList(list)

        bind.spProject.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                val sp = list[position]
                pjId = sp.pjId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this@HireActivity)
        presenter?.callService(sharedPref.getIdCompany())
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Hire"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initTextWatcher() {
        bind.etPrice.addTextChangedListener(MyTextWatcher(bind.etPrice))
        bind.etMessage.addTextChangedListener(MyTextWatcher(bind.etMessage))
    }

    private fun setProjectAdapter() {
        val adapter = ProjectAdapter(this@HireActivity)
        bind.spProject.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@HireActivity).get(HireViewModel::class.java)
        viewModel.setService(createApi(this@HireActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@HireActivity, {
            bind.btnProcessHire.visibility = View.GONE
            bind.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@HireActivity, {
            if (it) {
                setResult(RESULT_OK)
                this@HireActivity.finish()

                bind.progressBar.visibility = View.GONE
                bind.btnProcessHire.visibility = View.VISIBLE
            } else {
                bind.progressBar.visibility = View.GONE
                bind.btnProcessHire.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@HireActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@HireActivity, {
            noticeToast(it)
        })
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_price -> valPrice(bind.inputLayoutPrice, bind.etPrice)
                R.id.et_message -> valMessage(bind.inputLayoutMessage, bind.etMessage)
            }
        }
    }
}