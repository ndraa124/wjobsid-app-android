package com.id124.wjobsid.activity.detail_project

import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityDetailProjectBinding
import com.id124.wjobsid.model.hire.HireModel
import com.id124.wjobsid.model.hire.HireResponse
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.service.HireApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailProjectActivity : BaseActivityCoroutine<ActivityDetailProjectBinding>(), View.OnClickListener {
    private var hrId: Int? = null
    private var hrStatus: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_project
        super.onCreate(savedInstanceState)
        hrId = intent.getIntExtra("hr_id", 0)
        hrStatus = intent.getStringExtra("hr_status")

        if (hrStatus != "wait") {
            bind.btnApprove.visibility = View.GONE
            bind.btnCancel.visibility = View.GONE
        }

        setToolbarActionBar()
        setIntentData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_approve -> {
                approveHire()
            }
            R.id.btn_cancel -> {
                rejectHire()
            }
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Hiring Project"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setIntentData() {
        bind.hire = HireModel(
            hrId = hrId,
            enId = intent.getIntExtra("en_id", 0),
            pjId = intent.getIntExtra("pj_id", 0),
            hrPrice = intent.getLongExtra("hr_price", 0),
            hrMessage = intent.getStringExtra("hr_message"),
            hrStatus = hrStatus,
            hrDateConfirm = intent.getStringExtra("hr_date_confirm"),
            pjProjectName = intent.getStringExtra("pj_project_name"),
            pjDescription = intent.getStringExtra("pj_description"),
            pjDeadline = intent.getStringExtra("pj_deadline"),
            cnCompany = intent.getStringExtra("cn_company"),
            cnField = intent.getStringExtra("cn_field"),
            cnCity = intent.getStringExtra("cn_city")
        )

        bind.imageUrl = BASE_URL_IMAGE + intent.getStringExtra("cn_profile")
    }

    private fun approveHire() {
        val service = createApi<HireApiService>(this@DetailProjectActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateHireStatus(
                        hrId = hrId!!,
                        hrStatus = "approve"
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is HireResponse) {
                noticeToast("Hire is approved")
                setResult(RESULT_OK)
                this@DetailProjectActivity.finish()
            }
        }
    }

    private fun rejectHire() {
        val service = createApi<HireApiService>(this@DetailProjectActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateHireStatus(
                        hrId = hrId!!,
                        hrStatus = "reject"
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is HireResponse) {
                noticeToast("Hire is rejected")
                setResult(RESULT_OK)
                this@DetailProjectActivity.finish()
            }
        }
    }
}