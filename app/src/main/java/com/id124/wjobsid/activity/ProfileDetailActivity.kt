package com.id124.wjobsid.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.id124.wjobsid.helper.ViewPagerAdapter
import com.id124.wjobsid.R
import com.id124.wjobsid.fragment.ExperienceFragment
import com.id124.wjobsid.fragment.PortfolioFragment
import com.id124.wjobsid.helper.SharedPreference
import kotlinx.android.synthetic.main.activity_profile_detail.*

class ProfileDetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)
        sharedPreference = SharedPreference(this@ProfileDetailActivity)

        tab_layout.setupWithViewPager(view_pager)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(PortfolioFragment(), "Portfolio")
        adapter.addFrag(ExperienceFragment(), "Experience")
        view_pager.adapter = adapter

        if (sharedPreference.getLevel() == 0) {
            btn_hire.visibility = View.GONE
        } else {
            btn_hire.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_hire -> {
                Log.d("msg", "Hiring Process")
            }
        }
    }
}