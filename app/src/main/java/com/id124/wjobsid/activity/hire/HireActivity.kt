package com.id124.wjobsid.activity.hire

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.hire.adapter.ProjectAdapter
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityHireBinding
import com.id124.wjobsid.model.hire.HireResponse
import com.id124.wjobsid.model.project.ProjectModel
import com.id124.wjobsid.model.project.ProjectResponse
import com.id124.wjobsid.service.HireApiService
import com.id124.wjobsid.service.ProjectApiService
import com.id124.wjobsid.util.form_validate.ValidateHire.Companion.valMessage
import com.id124.wjobsid.util.form_validate.ValidateHire.Companion.valPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HireActivity : BaseActivityCoroutine<ActivityHireBinding>(), View.OnClickListener {
    private var pjId: Int? = 0
    private var enId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_hire
        super.onCreate(savedInstanceState)
        enId = intent.getIntExtra("en_id", 0)

        Log.d("msg", "ID ENNGINEER: $enId")

        setToolbarActionBar()
        initTextWatcher()
        setProjectAdapter()
        setProject()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_process_hire -> {
                when {
                    !valPrice(bind.inputLayoutPrice, bind.etPrice) -> {}
                    !valMessage(bind.inputLayoutMessage, bind.etMessage) -> {}
                    else -> {
                        createHire()
                    }
                }
            }
            R.id.ln_back -> {
                this@HireActivity.finish()
            }
        }
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

    private fun setProject() {
        val service = createApi<ProjectApiService>(this@HireActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllProject(sharedPref.getIdCompany())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is ProjectResponse) {
                val listData = response.data.map {
                    ProjectModel(
                        pjId = it.pjId,
                        cnId = it.cnId,
                        pjProjectName = it.pjProjectName,
                        pjDescription = it.pjDescription,
                        pjDeadline = it.pjDeadline,
                        pjImage = it.pjImage,
                    )
                }

                (bind.spProject.adapter as ProjectAdapter).addList(listData)

                bind.spProject.onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                        val sp = listData[position]
                        pjId = sp.pjId
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun setProjectAdapter() {
        val adapter = ProjectAdapter(this@HireActivity)
        bind.spProject.adapter = adapter
    }

    private fun createHire() {
        val service = createApi<HireApiService>(this@HireActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.createHire(
                        enId = enId!!,
                        pjId = pjId!!,
                        hrPrice = bind.etPrice.text.toString().toLong(),
                        hrMessage = bind.etMessage.text.toString()
                    )
                } catch (t: Throwable) {
                    Log.e("msg", "${t.message}")
                }
            }

            if (response is HireResponse) {
                noticeToast("Hire success")
                this@HireActivity.finish()
            }
        }
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