package com.id124.wjobsid.activity.portfolio

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id124.wjobsid.model.portfolio.PortfolioResponse
import com.id124.wjobsid.service.PortfolioApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class PortfolioViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: PortfolioApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: PortfolioApiService) {
        this@PortfolioViewModel.service = service
    }

    fun serviceCreateApi(
        enId: RequestBody,
        prApp: RequestBody,
        prDescription: RequestBody,
        prLinkPub: RequestBody,
        prLinkRepo: RequestBody,
        prWorkPlace: RequestBody,
        prType: RequestBody,
        image: MultipartBody.Part
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.createPortfolio(
                        enId = enId,
                        prApp = prApp,
                        prDescription = prDescription,
                        prLinkPub = prLinkPub,
                        prLinkRepo = prLinkRepo,
                        prWorkPlace = prWorkPlace,
                        prType = prType,
                        image = image
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to add data!"
                            }
                            e.code() == 404 -> {
                                onFailLiveData.value = "Image to large!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is PortfolioResponse) {
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
        prId: Int,
        prApp: RequestBody,
        prDescription: RequestBody,
        prLinkPub: RequestBody,
        prLinkRepo: RequestBody,
        prWorkPlace: RequestBody,
        prType: RequestBody,
        image: MultipartBody.Part? = null
    ) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.updatePortfolio(
                        prId = prId,
                        prApp = prApp,
                        prDescription = prDescription,
                        prLinkPub = prLinkPub,
                        prLinkRepo = prLinkRepo,
                        prWorkPlace = prWorkPlace,
                        prType = prType,
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

            if (response is PortfolioResponse) {
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

    fun serviceDeleteApi(prId: Int) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.deletePortfolio(
                        prId = prId
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

            if (response is PortfolioResponse) {
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