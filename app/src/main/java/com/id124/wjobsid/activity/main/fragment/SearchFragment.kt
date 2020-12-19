package com.id124.wjobsid.activity.main.fragment

import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseFragment
import com.id124.wjobsid.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_search
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}