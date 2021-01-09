package com.id124.wjobsid.activity.experience

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id124.wjobsid.model.experience.ExperienceResponse
import com.id124.wjobsid.service.ExperienceApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ExperienceViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: ExperienceApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ExperienceApiService) {
        this@ExperienceViewModel.service = service
    }

    fun serviceCreateApi(
        enId: Int,
        exPosition: String,
        exCompany: String,
        exStart: String,
        exEnd: String,
        exDescription: String,
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.createExperience(
                        enId = enId,
                        exPosition = exPosition,
                        exCompany = exCompany,
                        exStart = exStart,
                        exEnd = exEnd,
                        exDescription = exDescription
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

            if (response is ExperienceResponse) {
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
        exId: Int,
        exPosition: String,
        exCompany: String,
        exStart: String,
        exEnd: String,
        exDescription: String,
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateExperience(
                        exId = exId,
                        exPosition = exPosition,
                        exCompany = exCompany,
                        exStart = exStart,
                        exEnd = exEnd,
                        exDescription = exDescription
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

            if (response is ExperienceResponse) {
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

    fun serviceDeleteApi(exId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.deleteExperience(
                        exId = exId
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

            if (response is ExperienceResponse) {
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
}