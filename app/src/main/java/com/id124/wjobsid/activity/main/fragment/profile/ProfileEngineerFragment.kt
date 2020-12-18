package com.id124.wjobsid.activity.main.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseFragment
import com.id124.wjobsid.activity.profile.fragment.ExperienceFragment
import com.id124.wjobsid.activity.profile.fragment.PortfolioFragment
import com.id124.wjobsid.activity.settings.SettingsActivity
import com.id124.wjobsid.activity.skill.SkillActivity
import com.id124.wjobsid.databinding.FragmentProfileEngineerBinding
import com.id124.wjobsid.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_profile_company.view.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.view.*

class ProfileEngineerFragment : BaseFragment<FragmentProfileEngineerBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_engineer
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContentViewEngineer(view)
    }

    private fun setContentViewEngineer(view: View) {
        bind.tabLayout.setupWithViewPager(view.view_pager)
        val adapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFrag(PortfolioFragment(), "Portfolio")
        adapter.addFrag(ExperienceFragment(), "Experience")
        bind.viewPager.adapter = adapter

        bind.btnEditEngineer.setOnClickListener(this@ProfileEngineerFragment)
        bind.btnLogout.setOnClickListener(this@ProfileEngineerFragment)
        bind.ivAddSkill.setOnClickListener(this@ProfileEngineerFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_engineer -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.btn_logout -> {
                sharedPref.accountLogout()
            }
            R.id.iv_add_skill -> {
                startActivity(Intent(activity, SkillActivity::class.java))
            }
        }
    }
}