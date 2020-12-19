package com.id124.wjobsid.activity.main.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseFragment
import com.id124.wjobsid.activity.github.GithubActivity
import com.id124.wjobsid.activity.main.adapter.HomeEngineerAdapter
import com.id124.wjobsid.activity.profile.ProfileDetailActivity
import com.id124.wjobsid.databinding.FragmentHomeBinding
import com.id124.wjobsid.model.AccountModel
import com.id124.wjobsid.model.EngineerModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_home
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.accountModel = AccountModel(ac_name = userDetail["AC_NAME"])
        setupWebDevRecyclerView()
        setupAndroidDevRecyclerView()

        bind.ivGithub.setOnClickListener(this@HomeFragment)
    }

    private fun setupWebDevRecyclerView() {
        bind.rvWebDeveloper.layoutManager = LinearLayoutManager(activity, VERTICAL, false)

        val engineerModel: ArrayList<EngineerModel> = ArrayList()
        engineerModel.add(EngineerModel(
            ac_name = "Mark Winsen",
            en_job_title = "Web Developer",
            en_profile = "http://192.168.43.123:3000/images/IMG-1606113498604.jpg"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Jin Kazama",
            en_job_title = "Web Developer",
            en_profile = "http://192.168.43.123:3000/images/IMG-1606113498604.jpg"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Xiou You",
            en_job_title = "Web Developer",
            en_profile = "http://192.168.43.123:3000/images/IMG-1606113498604.jpg"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Vegapunk",
            en_job_title = "Web Developer",
            en_profile = "http://192.168.43.123:3000/images/IMG-1606113498604.jpg"
        ))

        val adapter = HomeEngineerAdapter(engineerModel)
        bind.rvWebDeveloper.adapter = adapter

        adapter.setOnItemClickCallback(object: HomeEngineerAdapter.OnItemClickCallback {
            override fun onItemClick(data: EngineerModel) {
                intents<ProfileDetailActivity>(activity)
            }
        })
    }

    private fun setupAndroidDevRecyclerView() {
        bind.rvAndroidDeveloper.layoutManager = LinearLayoutManager(activity, VERTICAL, false)

        val engineerModel: ArrayList<EngineerModel> = ArrayList()
        engineerModel.add(EngineerModel(
            ac_name = "Indra David Pesik",
            en_job_title = "Android Developer",
            en_profile = "http://192.168.43.123:3000/images/IMG-1606113498604.jpg"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "David William",
            en_job_title = "Android Developer",
            en_profile = "http://192.168.43.123:3000/images/IMG-1606113498604.jpg"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Julian Porajou",
            en_job_title = "Android Developer",
            en_profile = "http://192.168.43.123:3000/images/IMG-1606113498604.jpg"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Steve Jobs",
            en_job_title = "Android Developer",
            en_profile = "http://192.168.43.123:3000/images/IMG-1606113498604.jpg"
        ))

        val adapter = HomeEngineerAdapter(engineerModel)
        bind.rvAndroidDeveloper.adapter = adapter

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
}