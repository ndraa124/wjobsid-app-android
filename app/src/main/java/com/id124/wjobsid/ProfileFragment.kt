package com.id124.wjobsid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_profile_company.view.*
import kotlinx.android.synthetic.main.activity_profile_engineer.*
import kotlinx.android.synthetic.main.activity_profile_engineer.view.*

class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        sharedPreference = activity?.let { SharedPreference(it) }!!

        if (sharedPreference.getLevel() == 0) {
            val view: View = inflater.inflate(R.layout.activity_profile_engineer, container, false)

            view.tab_layout.setupWithViewPager(view.view_pager)
            val adapter = PerformancePagerAdapter(childFragmentManager)

            adapter.addFrag(PortfolioFragment(), "Portfolio")
            adapter.addFrag(ExperienceFragment(), "Experience")
            view.view_pager.adapter = adapter

//            view.view_pager.adapter = PerformancePagerAdapter(childFragmentManager)

            view.iv_settings_engineer.setOnClickListener(this@ProfileFragment)

            return view
        } else {
            val view: View = inflater.inflate(R.layout.activity_profile_company, container, false)

            view.iv_settings_company.setOnClickListener(this@ProfileFragment)

            return view
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_settings_engineer -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.iv_settings_company -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
        }
    }
}