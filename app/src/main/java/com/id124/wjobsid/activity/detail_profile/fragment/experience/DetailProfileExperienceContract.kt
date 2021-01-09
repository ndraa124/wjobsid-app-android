package com.id124.wjobsid.activity.detail_profile.fragment.experience

import com.id124.wjobsid.model.experience.ExperienceModel

interface DetailProfileExperienceContract {
    interface View {
        fun onResultSuccess(list: List<ExperienceModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(enId: Int?)
    }
}