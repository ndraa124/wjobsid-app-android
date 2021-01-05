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
import com.id124.wjobsid.model.engineer.EngineerResponse
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
        getAllEngineer()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_github -> {
                intents<GithubActivity>(activity)
            }
        }
    }

    private fun setupWebDevRecyclerView() {
        bind.rvEngineer.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        bind.rvEngineer.isNestedScrollingEnabled = false

        val adapter = HomeEngineerAdapter()
        adapter.notifyDataSetChanged()
        bind.rvEngineer.adapter = adapter

        adapter.setOnItemClickCallback(object: HomeEngineerAdapter.OnItemClickCallback {
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

    private fun getAllEngineer() {
        val service = createApi<EngineerApiService>(activity)

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
                    EngineerModel(
                        enId = it.enId,
                        acId = it.acId,
                        acName = it.acName,
                        enJobTitle = it.enJobTitle,
                        enJobType = it.enJobType,
                        enDomicile = it.enDomicile,
                        enDescription = it.enDescription,
                        enProfile = it.enProfile
                    )
                }

                (bind.rvEngineer.adapter as HomeEngineerAdapter).addList(list)
            }
        }
    }
}