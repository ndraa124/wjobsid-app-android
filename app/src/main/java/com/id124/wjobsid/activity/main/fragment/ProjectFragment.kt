package com.id124.wjobsid.activity.main.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseFragment
import com.id124.wjobsid.databinding.FragmentProjectBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_project
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarActionBar()
    }

    private fun setToolbarActionBar() {
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(bind.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = "Project"
        bind.toolbar.setNavigationOnClickListener {
            tb.onBackPressed()
        }
    }
}