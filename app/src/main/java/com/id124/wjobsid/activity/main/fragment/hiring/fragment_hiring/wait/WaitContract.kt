package com.id124.wjobsid.activity.main.fragment.hiring.fragment_hiring.wait

import com.id124.wjobsid.model.hire.HireModel

interface WaitContract {
    interface View {
        fun onResultSuccess(list: List<HireModel>)
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