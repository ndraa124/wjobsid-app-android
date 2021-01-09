package com.id124.wjobsid.activity.main.fragment.hiring

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.main.fragment.hiring.fragment_hiring.approve.ApproveFragment
import com.id124.wjobsid.activity.main.fragment.hiring.fragment_hiring.reject.RejectFragment
import com.id124.wjobsid.activity.main.fragment.hiring.fragment_hiring.wait.WaitFragment
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentHiringBinding
import com.id124.wjobsid.util.ViewPagerAdapter

class HiringFragment : BaseFragmentCoroutine<FragmentHiringBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_hiring
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarActionBar()
        setContentViewHiring()
    }

    private fun setToolbarActionBar() {
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(bind.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = "Hiring Project"
    }

    private fun setContentViewHiring() {
        bind.tabLayout.setupWithViewPager(bind.viewPager)
        val adapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFrag(WaitFragment(), "Wait")
        adapter.addFrag(ApproveFragment(), "Approve")
        adapter.addFrag(RejectFragment(), "Reject")
        bind.viewPager.adapter = adapter
    }
}