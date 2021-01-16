package com.id124.wjobsid.activity.detail_profile

import android.util.Log
import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.model.hire.HireResponse
import com.id124.wjobsid.model.skill.SkillModel
import com.id124.wjobsid.model.skill.SkillResponse
import com.id124.wjobsid.service.AccountApiService
import com.id124.wjobsid.service.EngineerApiService
import com.id124.wjobsid.service.HireApiService
import com.id124.wjobsid.service.SkillApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ProfileDetailPresenter(
    private val serviceAccount: AccountApiService,
    private val serviceEngineer: EngineerApiService,
    private val serviceSkill: SkillApiService,
    private val serviceHire: HireApiService
) : CoroutineScope,
    ProfileDetailContract.Presenter {

    private var view: ProfileDetailContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: ProfileDetailContract.View) {
        this@ProfileDetailPresenter.view = view
    }

    override fun unbind() {
        this@ProfileDetailPresenter.view = null
    }

    override fun callServiceAccount(acId: Int?) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceAccount.detailAccount(acId = acId!!)
                } catch (t: Throwable) {
                    withContext(Dispatchers.Main) {
                        Log.d("msg", "${t.message}")
                    }
                }
            }

            if (response is AccountResponse) {
                if (response.success) {
                    val data = response.data[0]

                    view?.onResultSuccessAccount(data)
                }
            }
        }
    }

    override fun callServiceEngineer(acId: Int?) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceEngineer.getDetailEngineer(acId = acId!!)
                } catch (t: Throwable) {
                    withContext(Dispatchers.Main) {
                        Log.d("msg", "${t.message}")
                    }
                }
            }

            if (response is EngineerResponse) {
                if (response.success) {
                    val data = response.data[0]

                    view?.onResultSuccessEngineer(data)
                }
            }
        }
    }

    override fun callServiceSkill(enId: Int?) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceSkill.getAllSkill(enId = enId!!)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No data skill!")
                            }
                            e.code() == 400 -> {
                                view?.onResultFail("expired")
                            }
                            else -> {
                                view?.onResultFail("Server under maintenance!")
                            }
                        }
                    }
                }
            }

            if (response is SkillResponse) {
                view?.hideLoading()

                if (response.success) {
                    val list = response.data.map {
                        SkillModel(
                            sk_id = it.sk_id,
                            sk_skill_name = it.skSkillName
                        )
                    }

                    view?.onResultSuccessSkill(list)
                } else {
                    view?.onResultFail(response.message)
                }
            }
        }
    }

    override fun callServiceIsHire(cnId: Int?, enId: Int?) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceHire.checkIsHire(
                        cnId = cnId!!,
                        enId = enId!!
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFailHire("No Data Hire!")
                            }
                            e.code() == 400 -> {
                                view?.onResultFailHire("expired")
                            }
                            else -> {
                                view?.onResultFailHire("Server under maintenance!")
                            }
                        }
                    }
                }
            }

            if (response is HireResponse) {
                view?.hideLoading()

                if (response.success) {
                    view?.onResultSuccessHire()
                } else {
                    view?.onResultFailHire(response.message)
                }
            }
        }
    }
}