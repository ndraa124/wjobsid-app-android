package com.id124.wjobsid.activity.main.fragment.profile.engineer.fragment_engineer_user.portfolio

import com.id124.wjobsid.model.portfolio.PortfolioModel

interface PortfolioContract {
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