package com.id124.wjobsid.activity.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseFragment
import com.id124.wjobsid.activity.profile.ProfileDetailActivity
import com.id124.wjobsid.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_home
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.listWebDeveloper1.setOnClickListener(this@HomeFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.list_web_developer_1 -> {
                sharedPref.createInDetail(1)
                startActivity(Intent(activity, ProfileDetailActivity::class.java))
            }
        }
    }
}