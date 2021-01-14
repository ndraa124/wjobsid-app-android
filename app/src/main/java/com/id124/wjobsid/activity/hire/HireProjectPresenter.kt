package com.id124.wjobsid.activity.hire

import com.id124.wjobsid.model.project.ProjectModel
import com.id124.wjobsid.model.project.ProjectResponse
import com.id124.wjobsid.service.ProjectApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class HireProjectPresenter(private val service: ProjectApiService) : CoroutineScope, HireProjectContract.Presenter {
    private var view: HireProjectContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: HireProjectContract.View) {
        this@HireProjectPresenter.view = view
    }

    override fun unbind() {
        this@HireProjectPresenter.view = null
    }

    override fun callService(cnId: Int) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllProject(cnId)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        when {
                            e.code() == 404 -> {
                                view?.onResultFail("No Data Project!")
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

            if (response is ProjectResponse) {
                if (response.success) {
                    val list = response.data.map {
                        ProjectModel(
                            pjId = it.pjId,
                            cnId = it.cnId,
                            pjProjectName = it.pjProjectName,
                            pjDescription = it.pjDescription,
                            pjDeadline = it.pjDeadline.split('T')[0],
                            pjImage = it.pjImage,
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