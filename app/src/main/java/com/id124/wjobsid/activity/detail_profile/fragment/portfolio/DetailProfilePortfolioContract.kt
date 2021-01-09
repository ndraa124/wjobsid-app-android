package com.id124.wjobsid.activity.detail_profile.fragment.portfolio

import com.id124.wjobsid.model.portfolio.PortfolioModel

interface DetailProfilePortfolioContract {
    interface View {
        fun onResultSuccess(list: List<PortfolioModel>)
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