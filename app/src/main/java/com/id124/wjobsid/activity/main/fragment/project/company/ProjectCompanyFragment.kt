package com.id124.wjobsid.activity.main.fragment.project.company

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.main.fragment.profile.engineer.ProfileEngineerFragment
import com.id124.wjobsid.activity.main.fragment.project.company.adapter.ProjectCompanyAdapter
import com.id124.wjobsid.activity.project.ProjectActivity
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentCompanyProjectBinding
import com.id124.wjobsid.model.project.ProjectModel

class ProjectCompanyFragment : BaseFragmentCoroutine<FragmentCompanyProjectBinding>(), ProjectCompanyContract.View {
    private var presenter: ProjectCompanyPresenter? = null

    companion object {
        const val INTENT_ADD = 100
        const val INTENT_EDIT = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_company_project
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProjectCompanyPresenter(createApi(activity))

        setToolbarActionBar()
        setCompanyProjectRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ProfileEngineerFragment.INTENT_ADD && resultCode == Activity.RESULT_OK) {
            presenter?.callService(sharedPref.getIdCompany())
        } else if (requestCode == ProfileEngineerFragment.INTENT_EDIT && resultCode == Activity.RESULT_OK) {
            presenter?.callService(sharedPref.getIdCompany())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_project_company_fragment, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                intentsResults<ProjectActivity>(activity, INTENT_ADD)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResultSuccess(list: List<ProjectModel>) {
        (bind.rvProject.adapter as ProjectCompanyAdapter).addList(list)
        bind.rvProject.visibility = View.VISIBLE
        bind.tvDataNotFound.visibility = View.GONE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            bind.rvProject.visibility = View.GONE
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
        presenter?.bindToView(this@ProjectCompanyFragment)
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
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(bind.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = "Project"
    }

    private fun setCompanyProjectRecyclerView() {
        bind.rvProject.isNestedScrollingEnabled = false
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
}