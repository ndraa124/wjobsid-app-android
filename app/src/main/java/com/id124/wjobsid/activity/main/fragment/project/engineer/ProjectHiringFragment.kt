package com.id124.wjobsid.activity.main.fragment.project.engineer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_project.DetailProjectActivity
import com.id124.wjobsid.activity.main.fragment.profile.engineer.ProfileEngineerFragment
import com.id124.wjobsid.activity.main.fragment.project.engineer.adapter.ProjectHiringAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentHiringProjectBinding
import com.id124.wjobsid.model.hire.HireModel

class ProjectHiringFragment : BaseFragmentCoroutine<FragmentHiringProjectBinding>(), ProjectHiringContract.View, SwipeRefreshLayout.OnRefreshListener{
    private var presenter: ProjectHiringPresenter? = null

    companion object {
        const val INTENT_EDIT = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_hiring_project
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProjectHiringPresenter(createApi(activity))

        setToolbarActionBar()
        setHiringProjectRecyclerView()
    }

    override fun onRefresh() {
        presenter?.callService(sharedPref.getIdEngineer())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ProfileEngineerFragment.INTENT_ADD && resultCode == Activity.RESULT_OK) {
            presenter?.callService(sharedPref.getIdEngineer())
        } else if (requestCode == ProfileEngineerFragment.INTENT_EDIT && resultCode == Activity.RESULT_OK) {
            presenter?.callService(sharedPref.getIdEngineer())
        }
    }

    override fun onResultSuccess(list: List<HireModel>) {
        (bind.rvHiring.adapter as ProjectHiringAdapter).addList(list)
        bind.rvHiring.visibility = View.VISIBLE
        bind.tvDataNotFound.visibility = View.GONE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            bind.rvHiring.visibility = View.GONE
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
        presenter?.bindToView(this@ProjectHiringFragment)
        presenter?.callService(sharedPref.getIdEngineer())
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
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(bind.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = "Hiring Project"
    }

    private fun setHiringProjectRecyclerView() {
        bind.rvHiring.isNestedScrollingEnabled = false
        bind.rvHiring.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ProjectHiringAdapter()
        bind.rvHiring.adapter = adapter

        adapter.setOnItemClickCallback(object : ProjectHiringAdapter.OnItemClickCallback {
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
                intent.putExtra("cn_company", data.cnCompany)
                intent.putExtra("cn_field", data.cnField)
                intent.putExtra("cn_city", data.cnCity)
                intent.putExtra("cn_profile", data.cnProfile)
                startActivityForResult(intent, INTENT_EDIT)
            }
        })
    }
}