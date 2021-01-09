package com.id124.wjobsid.activity.main.fragment.hiring.fragment_hiring.approve

import com.id124.wjobsid.model.hire.HireModel
import com.id124.wjobsid.model.hire.HireResponse
import com.id124.wjobsid.service.HireApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ApprovePresenter(private val service: HireApiService) : CoroutineScope, ApproveContract.Presenter {
    private var view: ApproveContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: ApproveContract.View) {
        this@ApprovePresenter.view = view
    }

    override fun unbind() {
        this@ApprovePresenter.view = null
    }

    override fun callService(cnId: Int) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllHireCompany(
                        cnId = cnId,
                        status = "approve"
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No Data Approve Hire!")
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

            if (response is HireResponse) {
                view?.hideLoading()

                if (response.success) {
                    val list = response.data.map {
                        HireModel(
                            hrId = it.hrId,
                            enId = it.enId,
                            pjId = it.pjId,
                            hrPrice = it.hrPrice,
                            hrMessage = it.hrMessage,
                            hrStatus = it.hrStatus,
                            hrDateConfirm =it.pjDeadline.split('T')[0],
                            pjProjectName = it.pjProjectName,
                            pjDescription = it.pjDescription,
                            pjDeadline = it.pjDeadline.split('T')[0],
                            pjImage = it.pjImage,
                            cnCompany = it.cnCompany,
                            cnField = it.cnField,
                            cnCity = it.cnCity,
                            cnProfile = it.cnProfile,
                            enProfile = it.enProfile,
                            acName = it.acName,
                            acEmail = it.acEmail,
                            acPhone = it.acPhone,
                        )
                    }

                    view?.onResultSuccess(list)
                } else {
                    view?.onResultFail(response.message)
                }
            }
        }
    }
}