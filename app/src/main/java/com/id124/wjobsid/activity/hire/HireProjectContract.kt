package com.id124.wjobsid.activity.hire

import com.id124.wjobsid.model.project.ProjectModel

interface HireProjectContract {
    interface View {
        fun onResultSuccess(list: List<ProjectModel>)
        fun onResultFail(message: String)
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService(cnId: Int)
    }
}