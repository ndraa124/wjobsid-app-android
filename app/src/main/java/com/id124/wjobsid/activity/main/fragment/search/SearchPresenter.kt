package com.id124.wjobsid.activity.main.fragment.search

import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.service.EngineerApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class SearchPresenter(private val service: EngineerApiService) : CoroutineScope, SearchContract.Presenter {

    private var view: SearchContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: SearchContract.View) {
        this@SearchPresenter.view = view
    }

    override fun unbind() {
        this@SearchPresenter.view = null
    }

    override fun callServiceSearch(search: String?) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllEngineer(search = search)
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
                view?.hideLoading()

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
                            enProfile = it.enProfile
                        )
                    }

                    view?.onResultSuccess(list)
                } else {
                    view?.onResultFail(response.message)
                }
            }
        }
    }

    override fun callServiceFilter(filter: Int?) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getFilterEngineer(filter = filter)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("Data not found!")
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
                view?.hideLoading()

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
                            enProfile = it.enProfile
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