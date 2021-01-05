package com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.experience

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.experience.ExperienceActivity
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.ProfileEngineerFragment
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.experience.adapter.ProfileExperienceAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentExperienceBinding
import com.id124.wjobsid.model.experience.ExperienceModel
import com.id124.wjobsid.model.experience.ExperienceResponse
import com.id124.wjobsid.service.ExperienceApiService
import kotlinx.android.synthetic.main.fragment_experience.view.*
import kotlinx.android.synthetic.main.fragment_portfolio.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExperienceFragment : BaseFragmentCoroutine<FragmentExperienceBinding>(), View.OnClickListener {
    companion object {
        const val INTENT_ADD = 100
        const val INTENT_EDIT = 200
    }

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

        val adapter = ProfileExperienceAdapter()
        bind.rvExperience.adapter = adapter

        adapter.setOnItemClickCallback(object : ProfileExperienceAdapter.OnItemClickCallback {
            override fun onItemClick(data: ExperienceModel) {
                val intent = Intent(activity, ExperienceActivity::class.java)
                intent.putExtra("en_id", data.en_id)
                intent.putExtra("ex_id", data.ex_id)
                intent.putExtra("ex_position", data.ex_position)
                intent.putExtra("ex_company", data.ex_company)
                intent.putExtra("ex_start", data.ex_start)
                intent.putExtra("ex_end", data.ex_end)
                intent.putExtra("ex_description", data.ex_description)
                startActivityForResult(intent, INTENT_EDIT)
            }
        })
    }

    private fun setExperience() {
        val service = createApi<ExperienceApiService>(activity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllExperience(sharedPref.getIdEngineer())
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
                        ex_start = it.exStart.split('T')[0],
                        ex_end = it.exEnd.split('T')[0],
                        ex_description = it.exDescription
                    )
                }

                (bind.rvExperience.adapter as ProfileExperienceAdapter).addList(list)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ProfileEngineerFragment.INTENT_ADD && resultCode == Activity.RESULT_OK) {
            setExperience()
        } else if (requestCode == ProfileEngineerFragment.INTENT_EDIT && resultCode == Activity.RESULT_OK) {
            setExperience()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_experience -> {
                intentsResults<ExperienceActivity>(activity, INTENT_ADD)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setExperience()
    }
}