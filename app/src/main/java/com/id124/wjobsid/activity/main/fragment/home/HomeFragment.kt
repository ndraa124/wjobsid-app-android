package com.id124.wjobsid.activity.main.fragment.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.github.GithubActivity
import com.id124.wjobsid.activity.main.fragment.home.adapter.HomeEngineerAdapter
import com.id124.wjobsid.activity.detail_profile.ProfileDetailActivity
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentHomeBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.service.EngineerApiService
import com.id124.wjobsid.util.SharedPreference.Companion.AC_NAME
import kotlinx.coroutines.*

class HomeFragment : BaseFragmentCoroutine<FragmentHomeBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_home
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.ivGithub.setOnClickListener(this@HomeFragment)

        if (sharedPref.getLevelUser() == 0) {
            bind.title = "Login as Engineer"
        } else {
            bind.title = "Login as Company"
        }

        bind.accountModel = AccountModel(acName = "Hai, ${userDetail[AC_NAME]}")

        setupWebDevRecyclerView()
        getAllProject(view)
    }

    private fun setupWebDevRecyclerView() {
        bind.rvWeb.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = HomeEngineerAdapter()
        bind.rvWeb.adapter = adapter

        adapter.setOnItemClickCallback(object: HomeEngineerAdapter.OnItemClickCallback {
            override fun onItemClick(data: EngineerModel) {
                intents<ProfileDetailActivity>(activity)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_github -> {
                intents<GithubActivity>(activity)
            }
        }
    }

    private fun getAllProject(view: View) {
        val service = ApiClient.getApiClient(view.context).create(EngineerApiService::class.java)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllEngineer()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is EngineerResponse) {
                val list = response.data.map {
                    EngineerModel(it.enId, it.acId, it.acName, it.enJobTitle, it.enJobType, it.enDomicile, it.enDescription, it.enProfile)
                }

                (bind.rvWeb.adapter as HomeEngineerAdapter).addList(list)
            }
        }
    }
}