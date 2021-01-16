package com.id124.wjobsid.activity.detail_profile

import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.model.skill.SkillModel

interface ProfileDetailContract {
    interface View {
        fun onResultSuccessAccount(data: AccountResponse.AccountItem)
        fun onResultSuccessEngineer(data: EngineerResponse.EngineerItem)
        fun onResultSuccessSkill(list: List<SkillModel>)
        fun onResultSuccessHire(status: Boolean)
        fun onResultFail(message: String)
        fun onResultFailHire(message: String)
        fun showLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callServiceAccount(acId: Int?)
        fun callServiceEngineer(acId: Int?)
        fun callServiceSkill(enId: Int?)
        fun callServiceIsHire(cnId: Int?, enId: Int?)
    }
}