package com.id124.wjobsid.activity.main.fragment.home

import com.id124.wjobsid.model.engineer.EngineerModel

interface HomeContract {
    interface View {
        fun onResultSuccess(list: List<EngineerModel>)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callService()
    }
}