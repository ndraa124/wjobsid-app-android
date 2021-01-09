package com.id124.wjobsid.activity.main.fragment.profile.engineer.fragment_engineer_user.experience

import com.id124.wjobsid.model.experience.ExperienceModel

interface ExperienceContract {
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