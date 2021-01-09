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

class DetailProfileExperienceFragment(private val enId: Int) : BaseFragmentCoroutine<FragmentExperienceBinding>(), DetailProfileExperienceContract.View {
    private var presenter: DetailProfileExperiencePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_experience
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailProfileExperiencePresenter(createApi(activity))

        if (sharedPref.getInDetail() == 0) {
            bind.btnAddExperience.visibility = View.VISIBLE
        } else {
            bind.btnAddExperience.visibility = View.GONE
        }

        setupExperienceRecyclerView()
    }

    override fun onResultSuccess(list: List<ExperienceModel>) {
        (bind.rvExperience.adapter as ProfileDetailExperienceAdapter).addList(list)
        bind.rvExperience.visibility = View.VISIBLE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            bind.rvExperience.visibility = View.GONE
            bind.tvDataNotFound.visibility = View.VISIBLE
            bind.dataNotFound = message
        }
    }

    override fun showLoading() {
        bind.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        bind.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this@DetailProfileExperienceFragment)
        presenter?.callService(enId)
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setupExperienceRecyclerView() {
        bind.rvExperience.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ProfileDetailExperienceAdapter()
        bind.rvExperience.adapter = adapter
    }
}