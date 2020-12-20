package com.id124.wjobsid.activity.main.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseFragment
import com.id124.wjobsid.activity.github.GithubActivity
import com.id124.wjobsid.activity.main.adapter.HomeEngineerAdapter
import com.id124.wjobsid.activity.profile.ProfileDetailActivity
import com.id124.wjobsid.databinding.FragmentHomeBinding
import com.id124.wjobsid.model.AccountModel
import com.id124.wjobsid.model.EngineerModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener {
    private var engineerModel = ArrayList<EngineerModel>()

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
        bind.rvWeb.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        getWebDataEngineer()

        val adapter = HomeEngineerAdapter(engineerModel)
        bind.rvWeb.adapter = adapter

        adapter.setOnItemClickCallback(object: HomeEngineerAdapter.OnItemClickCallback {
            override fun onItemClick(data: EngineerModel) {
                intents<ProfileDetailActivity>(activity)
            }
        })
    }

    private fun setupAndroidDevRecyclerView() {
        bind.rvAndroid.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        getAndroidDataEngineer()

        val adapter = HomeEngineerAdapter(engineerModel)
        bind.rvAndroid.adapter = adapter

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

    private fun getWebDataEngineer() {
        engineerModel = ArrayList()

        engineerModel.add(EngineerModel(
            ac_name = "Indra David Pesik",
            en_job_title = "Web Developer",
            en_profile = "https://cdn1-production-images-kly.akamaized.net/rrn1fq4Gul21KLoZQ6gMOsK4nt8=/640x360/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/1108592/original/042718400_1452576907-12012016-android.jpg"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "David William",
            en_job_title = "Web Developer",
            en_profile = "https://pkrs.rstugurejo.jatengprov.go.id/wp-content/uploads/2019/04/user.png"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Julian Porajou",
            en_job_title = "Web Developer",
            en_profile = "https://cdn1-production-images-kly.akamaized.net/rrn1fq4Gul21KLoZQ6gMOsK4nt8=/640x360/smart/filters:quality(75):strip_icc():format(jpeg)/kly-media-production/medias/1108592/original/042718400_1452576907-12012016-android.jpg"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Steve Jobs",
            en_job_title = "Web Developer",
            en_profile = "https://pkrs.rstugurejo.jatengprov.go.id/wp-content/uploads/2019/04/user.png"
        ))
    }

    private fun getAndroidDataEngineer() {
        engineerModel = ArrayList()

        engineerModel.add(EngineerModel(
            ac_name = "Indra David Pesik",
            en_job_title = "Android Developer",
            en_profile = "https://info-menarik.net/wp-content/uploads/2014/10/User-Info-Menarik.png"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "David William",
            en_job_title = "Android Developer",
            en_profile = "https://info-menarik.net/wp-content/uploads/2014/10/User-Info-Menarik.png"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Julian Porajou",
            en_job_title = "Android Developer",
            en_profile = "https://info-menarik.net/wp-content/uploads/2014/10/User-Info-Menarik.png"
        ))
        engineerModel.add(EngineerModel(
            ac_name = "Steve Jobs",
            en_job_title = "Android Developer",
            en_profile = "https://info-menarik.net/wp-content/uploads/2014/10/User-Info-Menarik.png"
        ))
    }
}