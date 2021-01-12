package com.id124.wjobsid.activity.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id124.wjobsid.model.hire.HireResponse
import com.id124.wjobsid.model.project.ProjectResponse
import com.id124.wjobsid.service.HireApiService
import com.id124.wjobsid.service.ProjectApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ProjectViewModel : ViewModel(), CoroutineScope {
    private lateinit var serviceProject: ProjectApiService
    private lateinit var serviceHire: HireApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onSuccessHireLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val onFailHireLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setServiceProject(service: ProjectApiService) {
        this@ProjectViewModel.serviceProject = service
    }

    fun setServiceHire(service: HireApiService) {
        this@ProjectViewModel.serviceHire = service
    }

    fun serviceCreateApi(
        cnId: RequestBody,
        pjProjectName: RequestBody,
        pjDeadline: RequestBody,
        pjDescription: RequestBody,
        image: MultipartBody.Part
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceProject.createProject(
                        cnId = cnId,
                        pjProjectName = pjProjectName,
                        pjDeadline = pjDeadline,
                        pjDescription = pjDescription,
                        image = image
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to add data!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is ProjectResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }

    fun serviceUpdateApi(
        pjId: Int,
        pjProjectName: RequestBody,
        pjDeadline: RequestBody,
        pjDescription: RequestBody,
        image: MultipartBody.Part? = null
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceProject.updateProject(
                        pjId = pjId,
                        pjProjectName = pjProjectName,
                        pjDeadline = pjDeadline,
                        pjDescription = pjDescription,
                        image = image
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 404 -> {
                                onFailLiveData.value = "Data not found!"
                            }
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to update data!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is ProjectResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }

    fun serviceDeleteApi(pjId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceProject.deleteProject(
                        pjId = pjId
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 404 -> {
                                onFailLiveData.value = "Data not found!"
                            }
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to delete data!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is ProjectResponse) {
                isLoadingLiveData.value = false

                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }

    fun serviceIsHireApi(pjId: Int?) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceHire.getAllHireByProject(
                        pjId = pjId!!
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        when {
                            e.code() == 404 -> {
                                onFailHireLiveData.value = "No Data Hire!"
                            }
                            e.code() == 400 -> {
                                onFailHireLiveData.value = "expired"
                            }
                            else -> {
                                onFailHireLiveData.value = "Server under maintenance!"
                            }
                        }
                    }
                }
            }

            if (response is HireResponse) {
                if (response.success) {
                    onSuccessHireLiveData.value = true
                } else {
                    onFailHireLiveData.value = response.message
                }
            }
        }
    }
}