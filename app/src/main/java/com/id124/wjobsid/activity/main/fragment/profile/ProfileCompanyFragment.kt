package com.id124.wjobsid.activity.main.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseFragment
import com.id124.wjobsid.activity.settings.SettingsActivity
import com.id124.wjobsid.databinding.FragmentProfileCompanyBinding
import kotlinx.android.synthetic.main.fragment_profile_company.view.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.view.*

class ProfileCompanyFragment : BaseFragment<FragmentProfileCompanyBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_company
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContentViewCompany()
    }

    private fun setContentViewCompany() {
        bind.btnEditCompany.setOnClickListener(this@ProfileCompanyFragment)
        bind.btnLogout.setOnClickListener(this@ProfileCompanyFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_company -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.btn_logout -> {
                logoutConf(activity)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(0)
    }
}