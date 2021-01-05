package com.id124.wjobsid.activity.detail_profile.fragment.experience

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.fragment.experience.adapter.ProfileDetailExperienceAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentExperienceBinding
import com.id124.wjobsid.model.experience.ExperienceModel
import com.id124.wjobsid.model.experience.ExperienceResponse
import com.id124.wjobsid.service.ExperienceApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailProfileExperienceFragment(private val enId: Int) : BaseFragmentCoroutine<FragmentExperienceBinding>() {
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
    }

    override fun onStart() {
        super.onStart()
        setExperience()
    }

    private fun setupExperienceRecyclerView() {
        bind.rvExperience.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ProfileDetailExperienceAdapter()
        bind.rvExperience.adapter = adapter
    }

    private fun setExperience() {
        val service = createApi<ExperienceApiService>(activity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllExperience(enId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is ExperienceResponse) {
                val list = response.data.map {
                    ExperienceModel(
                        ex_id = it.exId,
                        en_id = it.enId,
                        ex_position = it.exPosition,
                        ex_company = it.exCompany,
                        ex_start = it.exStart.split('T')[0] + " - ",
                        ex_end = it.exEnd.split('T')[0],
                        ex_description = it.exDescription
                    )
                }

                (bind.rvExperience.adapter as ProfileDetailExperienceAdapter).addList(list)
            }
        }
    }
}