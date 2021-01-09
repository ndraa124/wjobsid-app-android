package com.id124.wjobsid.activity.main.fragment.project.company

import com.id124.wjobsid.model.project.ProjectModel

interface ProjectCompanyContract {
    interface View {
        fun onResultSuccess(list: List<ProjectModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(cnId: Int)
    }
}