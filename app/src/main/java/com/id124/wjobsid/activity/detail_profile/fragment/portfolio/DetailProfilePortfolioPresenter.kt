package com.id124.wjobsid.activity.detail_profile.fragment.portfolio

import com.id124.wjobsid.model.portfolio.PortfolioModel
import com.id124.wjobsid.model.portfolio.PortfolioResponse
import com.id124.wjobsid.service.PortfolioApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class DetailProfilePortfolioPresenter(private val service: PortfolioApiService) : CoroutineScope, DetailProfilePortfolioContract.Presenter {

    private var view: DetailProfilePortfolioContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: DetailProfilePortfolioContract.View) {
        this@DetailProfilePortfolioPresenter.view = view
    }

    override fun unbind() {
        this@DetailProfilePortfolioPresenter.view = null
    }

    override fun callService(enId: Int?) {
        launch {
            view?.showLoading()

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllPortfolio(enId = enId!!)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()

                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No data portfolio!")
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

            if (response is PortfolioResponse) {
                view?.hideLoading()

                if (response.success) {
                    val list = response.data.map {
                        PortfolioModel(
                            pr_id = it.prId,
                            en_id = it.enId,
                            pr_app = it.prApp,
                            pr_description = it.prDescription,
                            pr_link_pub = it.prLinkPub,
                            pr_link_repo = it.prLinkRepo,
                            pr_work_place = it.prWorkPlace,
                            pr_type = it.prType,
                            pr_image = it.prImage
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