package com.id124.wjobsid.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.ExperienceActivity
import kotlinx.android.synthetic.main.fragment_experience.view.*

class ExperienceFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_experience, container, false)

        view.btn_add_experience.setOnClickListener(this@ExperienceFragment)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_experience -> {
                startActivity(Intent(activity, ExperienceActivity::class.java))
            }
        }
    }
}