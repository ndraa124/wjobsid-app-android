package com.id124.wjobsid.activity.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.activity.profile.fragment.ExperienceFragment
import com.id124.wjobsid.activity.profile.fragment.PortfolioFragment
import com.id124.wjobsid.databinding.ActivityProfileDetailBinding
import com.id124.wjobsid.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_profile_detail.*

class ProfileDetailActivity : BaseActivity<ActivityProfileDetailBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_profile_detail
        super.onCreate(savedInstanceState)

        setToolbarActionBar()
        initViewPager()

        if (sharedPref.getLevelUser() == 0) {
            bind.btnHire.visibility = View.GONE
        } else {
            bind.btnHire.visibility = View.VISIBLE
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_hire -> {
                Log.d("msg", "Hiring Process")
            }
        }
    }

    private fun initViewPager() {
        bind.tabLayout.setupWithViewPager(view_pager)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(PortfolioFragment(), "Portfolio")
        adapter.addFrag(ExperienceFragment(), "Experience")
        bind.viewPager.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(1)
    }
}