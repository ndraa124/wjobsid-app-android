package com.id124.wjobsid.activity.main.fragment.hiring.fragment_hiring.wait

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_project.DetailProjectActivity
import com.id124.wjobsid.activity.main.fragment.hiring.fragment_hiring.wait.adapter.WaitHireAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentWaitBinding
import com.id124.wjobsid.model.hire.HireModel

class WaitFragment : BaseFragmentCoroutine<FragmentWaitBinding>(), WaitContract.View {
    private var presenter: WaitPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_wait
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = WaitPresenter(createApi(activity))

        setHiringProjectRecyclerView()
    }

    override fun onResultSuccess(list: List<HireModel>) {
        (bind.rvHire.adapter as WaitHireAdapter).addList(list)
        bind.rvHire.visibility = View.VISIBLE
        bind.tvDataNotFound.visibility = View.GONE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            bind.rvHire.visibility = View.GONE
            bind.tvDataNotFound.visibility = View.VISIBLE
            bind.dataNotFound = message
        }
    }

    override fun showLoading() {
        bind.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        bind.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this@WaitFragment)
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

    private fun setHiringProjectRecyclerView() {
        bind.rvHire.isNestedScrollingEnabled = false
        bind.rvHire.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = WaitHireAdapter()
        bind.rvHire.adapter = adapter

        adapter.setOnItemClickCallback(object : WaitHireAdapter.OnItemClickCallback {
            override fun onItemClick(data: HireModel) {
                val intent = Intent(activity, DetailProjectActivity::class.java)
                intent.putExtra("hr_id", data.hrId)
                intent.putExtra("en_id", data.enId)
                intent.putExtra("pj_id", data.pjId)
                intent.putExtra("hr_price", data.hrPrice)
                intent.putExtra("hr_message", data.hrMessage)
                intent.putExtra("hr_status", data.hrStatus)
                intent.putExtra("hr_date_confirm", data.hrDateConfirm)
                intent.putExtra("pj_project_name", data.pjProjectName)
                intent.putExtra("pj_deadline", data.pjDeadline)
                intent.putExtra("pj_description", data.pjDescription)
                intent.putExtra("pj_image", data.pjImage)
                intent.putExtra("cn_company", data.cnCompany)
                intent.putExtra("cn_field", data.cnField)
                intent.putExtra("cn_city", data.cnCity)
                intent.putExtra("en_profile", data.enProfile)
                intent.putExtra("ac_name", data.acName)
                intent.putExtra("ac_email", data.acEmail)
                intent.putExtra("ac_phone", data.acPhone)
                startActivity(intent)
            }
        })
    }
}