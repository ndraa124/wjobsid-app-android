package com.id124.wjobsid.activity.main.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.ProfileDetailActivity
import com.id124.wjobsid.activity.github.GithubActivity
import com.id124.wjobsid.activity.main.fragment.home.adapter.HomeEngineerAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentHomeBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.util.SharedPreference.Companion.AC_NAME
import kotlinx.coroutines.*

class HomeFragment : BaseFragmentCoroutine<FragmentHomeBinding>(), HomeContract.View, View.OnClickListener {
    private var presenter: HomePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_home
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomePresenter(createApi(activity))

        bind.ivGithub.setOnClickListener(this@HomeFragment)

        if (sharedPref.getLevelUser() == 0) {
            bind.title = "Login as Engineer"
        } else {
            bind.title = "Login as Company"
        }

        bind.accountModel = AccountModel(acName = "Hai, ${userDetail[AC_NAME]}")
        setupWebDevRecyclerView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_github -> {
                intents<GithubActivity>(activity)
            }
        }
    }

    override fun onResultSuccess(list: List<EngineerModel>) {
        (bind.rvEngineer.adapter as HomeEngineerAdapter).addList(list)
        bind.rvEngineer.visibility = View.VISIBLE
        bind.tvDataNotFound.visibility = View.GONE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            bind.rvEngineer.visibility = View.GONE
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
        presenter?.bindToView(this@HomeFragment)
        presenter?.callService()
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setupWebDevRecyclerView() {
        bind.rvEngineer.isNestedScrollingEnabled = false
        bind.rvEngineer.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = HomeEngineerAdapter()
        bind.rvEngineer.adapter = adapter

        adapter.setOnItemClickCallback(object : HomeEngineerAdapter.OnItemClickCallback {
            override fun onItemClick(data: EngineerModel) {
                val intent = Intent(activity, ProfileDetailActivity::class.java)
                intent.putExtra("en_id", data.enId)
                intent.putExtra("ac_id", data.acId)
                intent.putExtra("ac_name", data.acName)
                intent.putExtra("en_job_title", data.enJobTitle)
                intent.putExtra("en_domicile", data.enDomicile)
                intent.putExtra("en_job_type", data.enJobType)
                intent.putExtra("en_description", data.enDescription)
                intent.putExtra("en_profile", data.enProfile)
                startActivity(intent)
            }
        })
    }
}