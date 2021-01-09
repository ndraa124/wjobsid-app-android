package com.id124.wjobsid.activity.main.fragment.project.engineer

import com.id124.wjobsid.model.hire.HireModel

interface ProjectHiringContract {
    interface View {
        fun onResultSuccess(list: List<HireModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(enId: Int)
    }
}