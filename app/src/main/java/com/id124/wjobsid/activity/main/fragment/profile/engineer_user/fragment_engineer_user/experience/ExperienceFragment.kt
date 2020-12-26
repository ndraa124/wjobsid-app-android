package com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.experience

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseFragment
import com.id124.wjobsid.activity.experience.ExperienceActivity
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.experience.adapter.ProfileExperienceAdapter
import com.id124.wjobsid.databinding.FragmentExperienceBinding
import com.id124.wjobsid.model.experience.ExperienceModel
import kotlinx.android.synthetic.main.fragment_experience.view.*
import kotlinx.android.synthetic.main.fragment_portfolio.view.*

class ExperienceFragment : BaseFragment<FragmentExperienceBinding>(), View.OnClickListener {
    private var experienceModel = ArrayList<ExperienceModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_experience
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sharedPref.getInDetail() == 0) {
            bind.btnAddExperience.visibility = View.VISIBLE
        } else {
            bind.btnAddExperience.visibility = View.GONE
        }

        setupExperienceRecyclerView()
        bind.btnAddExperience.setOnClickListener(this@ExperienceFragment)
    }

    private fun setupExperienceRecyclerView() {
        bind.rvExperience.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        experienceModel = ArrayList()
        experienceModel.add(
            ExperienceModel(
            ex_potition = "Web Developer",
            ex_company = "PT. Kawanua Tech",
            ex_start = "January 2019 - ",
            ex_end = "March 2020",
            ex_description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum erat orci, " +
                    "mollis nec gravida sed, ornare quis urna. Curabitur eu lacus fringilla, vestibulum risus at."
        )
        )
        experienceModel.add(
            ExperienceModel(
            ex_potition = "Android Developer",
            ex_company = "PT. Telkom Indonesia",
            ex_start = "April 2018 - ",
            ex_end = "June 2020",
            ex_description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum erat orci, " +
                    "mollis nec gravida sed, ornare quis urna. Curabitur eu lacus fringilla, vestibulum risus at."
        )
        )
        experienceModel.add(
            ExperienceModel(
            ex_potition = "Android Developer",
            ex_company = "PT. Bank Central Indonesia",
            ex_start = "June 2019 - ",
            ex_end = "September 2020",
            ex_description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum erat orci, " +
                    "mollis nec gravida sed, ornare quis urna. Curabitur eu lacus fringilla, vestibulum risus at."
        )
        )

        val adapter = ProfileExperienceAdapter(experienceModel)
        bind.rvExperience.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_experience -> {
                intents<ExperienceActivity>(activity)
            }
        }
    }
}