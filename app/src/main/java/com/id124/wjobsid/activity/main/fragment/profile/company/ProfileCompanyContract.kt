package com.id124.wjobsid.activity.main.fragment.profile.company

import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.company.CompanyResponse
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.model.skill.SkillModel

interface ProfileCompanyContract {
    interface View {
        fun onResultSuccessAccount(data: AccountResponse.AccountItem)
        fun onResultSuccessCompany(data: CompanyResponse.CompanyItem)
        fun onResultFail(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callServiceAccount(acId: Int?)
        fun callServiceCompany(acId: Int?)
    }
}