package com.id124.wjobsid.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.ProfileDetailActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        view.list_web_developer_1.setOnClickListener(this@HomeFragment)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.list_web_developer_1 -> {
                startActivity(Intent(activity, ProfileDetailActivity::class.java))
            }
        }
    }
}