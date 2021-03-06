package com.id124.wjobsid.activity.main.fragment.home

import android.util.Log
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.service.EngineerApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class HomePresenter(private val service: EngineerApiService) : CoroutineScope, HomeContract.Presenter {

    private var view: HomeContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: HomeContract.View) {
        this@HomePresenter.view = view
    }

    override fun unbind() {
        this@HomePresenter.view = null
    }

    override fun callService(page: Int) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllEngineer(
                        page = page
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No data engineer!")
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

            if (response is EngineerResponse) {
                if (response.success) {
                    val list = response.data.map {
                        EngineerModel(
                            enId = it.enId,
                            acId = it.acId,
                            acName = it.acName,
                            enJobTitle = it.enJobTitle,
                            enJobType = it.enJobType,
                            enDomicile = it.enDomicile,
                            enDescription = it.enDescription,
                            enProfile = it.enProfile,
                            enSkill = it.enSkill
                        )
                    }

                    view?.hideLoading()
                    view?.onResultSuccess(list, response.totalPages)
                } else {
                    view?.onResultFail(response.message)
                }
            }
        }
    }
}