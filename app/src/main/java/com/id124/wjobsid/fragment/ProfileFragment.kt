package com.id124.wjobsid.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.id124.wjobsid.PerformancePagerAdapter
import com.id124.wjobsid.R
import com.id124.wjobsid.helper.SharedPreference
import com.id124.wjobsid.activity.SettingsActivity
import com.id124.wjobsid.activity.SkillActivity
import kotlinx.android.synthetic.main.fragment_profile_company.view.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.view.*

class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreference = activity?.let { SharedPreference(it) }!!
        val view: View?

        if (sharedPreference.getLevel() == 0) {
            view = inflater.inflate(R.layout.fragment_profile_engineer, container, false)
            setContentViewEngineer(view)
        } else {
            view = inflater.inflate(R.layout.fragment_profile_company, container, false)
            setContentViewCompany(view)
        }

        return view
    }

    private fun setContentViewEngineer(view: View) {
        view.tab_layout.setupWithViewPager(view.view_pager)
        val adapter = PerformancePagerAdapter(childFragmentManager)

        adapter.addFrag(PortfolioFragment(), "Portfolio")
        adapter.addFrag(ExperienceFragment(), "Experience")
        view.view_pager.adapter = adapter

        view.iv_settings_engineer.setOnClickListener(this@ProfileFragment)
        view.iv_add_skill.setOnClickListener(this@ProfileFragment)
    }

    private fun setContentViewCompany(view: View) {
        view.iv_settings_company.setOnClickListener(this@ProfileFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_settings_engineer -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.iv_settings_company -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.iv_add_skill -> {
                startActivity(Intent(activity, SkillActivity::class.java))
            }
        }
    }
}