package com.id124.wjobsid.activity.main.fragment.profile.engineer.fragment_engineer_user.experience

import com.id124.wjobsid.model.experience.ExperienceModel
import com.id124.wjobsid.model.experience.ExperienceResponse
import com.id124.wjobsid.service.ExperienceApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ExperiencePresenter(private val service: ExperienceApiService) : CoroutineScope, ExperienceContract.Presenter {

    private var view: ExperienceContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: ExperienceContract.View) {
        this@ExperiencePresenter.view = view
    }

    override fun unbind() {
        this@ExperiencePresenter.view = null
    }

    override fun callService(enId: Int?) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllExperience(enId = enId!!)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No data experience!")
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

            if (response is ExperienceResponse) {
                view?.hideLoading()

                if (response.success) {
                    val list = response.data.map {
                        ExperienceModel(
                            ex_id = it.exId,
                            en_id = it.enId,
                            ex_position = it.exPosition,
                            ex_company = it.exCompany,
                            ex_start = it.exStart.split('T')[0],
                            ex_end = it.exEnd.split('T')[0],
                            ex_description = it.exDescription
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