package com.id124.wjobsid.activity.main.fragment.project

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_project.DetailProjectActivity
import com.id124.wjobsid.activity.main.fragment.project.adapter.ProjectCompanyAdapter
import com.id124.wjobsid.activity.main.fragment.project.adapter.ProjectHiringAdapter
import com.id124.wjobsid.activity.project.ProjectActivity
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentProjectBinding
import com.id124.wjobsid.model.hire.HireModel
import com.id124.wjobsid.model.hire.HireResponse
import com.id124.wjobsid.model.project.ProjectModel
import com.id124.wjobsid.model.project.ProjectResponse
import com.id124.wjobsid.service.HireApiService
import com.id124.wjobsid.service.ProjectApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProjectFragment : BaseFragmentCoroutine<FragmentProjectBinding>(), View.OnClickListener {
    companion object {
        const val INTENT_ADD = 100
        const val INTENT_EDIT = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_project
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarActionBar()

        if (sharedPref.getLevelUser() == 0) {
            bind.rvHiring.visibility = View.VISIBLE
            bind.rvProject.visibility = View.GONE
            bind.btnAddProject.visibility = View.GONE

            setHiringProjectRecyclerView()
        } else {
            bind.rvHiring.visibility = View.GONE
            bind.rvProject.visibility = View.VISIBLE
            bind.btnAddProject.visibility = View.VISIBLE
            bind.btnAddProject.setOnClickListener(this@ProjectFragment)

            setCompanyProjectRecyclerView()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_add_project -> {
                intentsResults<ProjectActivity>(activity, INTENT_ADD)
            }
        }
    }

    private fun setToolbarActionBar() {
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(bind.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        if (sharedPref.getLevelUser() == 0) {
            tb.supportActionBar?.title = "Hiring Project"
        } else {
            tb.supportActionBar?.title = "Project"
        }
    }

    private fun setCompanyProjectRecyclerView() {
        bind.rvProject.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ProjectCompanyAdapter()
        bind.rvProject.adapter = adapter

        adapter.setOnItemClickCallback(object : ProjectCompanyAdapter.OnItemClickCallback {
            override fun onItemClick(data: ProjectModel) {
                val intent = Intent(activity, ProjectActivity::class.java)
                intent.putExtra("pj_id", data.pjId)
                intent.putExtra("cn_id", data.cnId)
                intent.putExtra("pj_project_name", data.pjProjectName)
                intent.putExtra("pj_deadline", data.pjDeadline)
                intent.putExtra("pj_description", data.pjDescription)
                intent.putExtra("pj_image", data.pjImage)
                startActivityForResult(intent, INTENT_EDIT)
            }
        })
    }

    private fun setHiringProjectRecyclerView() {
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

    private fun setCompanyProject() {
        val service = createApi<ProjectApiService>(activity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllProject(sharedPref.getIdCompany())
                } catch (e: HttpException) {
                    activity?.runOnUiThread {
                        if (e.code() == 404) {
                            bind.tvDataNotFound.visibility = View.VISIBLE
                            bind.dataNotFound = "No Data Project!\nPlease Add Data First!"
                        } else {
                            noticeToast("Server under maintenance!")
                        }
                    }
                }
            }

            if (response is ProjectResponse) {
                val list = response.data.map {
                    ProjectModel(
                        pjId = it.pjId,
                        cnId = it.cnId,
                        pjProjectName = it.pjProjectName,
                        pjDescription = it.pjDescription,
                        pjDeadline = it.pjDeadline.split('T')[0],
                        pjImage = it.pjImage,
                    )
                }

                (bind.rvProject.adapter as ProjectCompanyAdapter).addList(list)
            }
        }
    }

    private fun setHiringProject() {
        val service = createApi<HireApiService>(activity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllHire(sharedPref.getIdEngineer())
                } catch (e: HttpException) {
                    if (e.code() == 404) {
                        bind.tvDataNotFound.visibility = View.VISIBLE
                        bind.dataNotFound = "No Data Hiring!"
                    } else {
                        noticeToast("Server under maintenance!")
                    }
                }
            }

            if (response is HireResponse) {
                val list = response.data.map {
                    HireModel(
                        hrId = it.hrId,
                        enId = it.enId,
                        pjId = it.pjId,
                        hrPrice = it.hrPrice,
                        hrMessage = it.hrMessage,
                        hrStatus = it.hrStatus,
                        hrDateConfirm = it.hrDateConfirm,
                        pjProjectName = it.pjProjectName,
                        pjDescription = it.pjDescription,
                        pjDeadline = it.pjDeadline.split('T')[0],
                        cnCompany = it.cnCompany,
                        cnField = it.cnField,
                        cnCity = it.cnCity,
                        cnProfile = it.cnProfile
                    )
                }

                (bind.rvHiring.adapter as ProjectHiringAdapter).addList(list)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getLevelUser() == 0) {
            setHiringProject()
        } else {
            setCompanyProject()
        }
    }
}