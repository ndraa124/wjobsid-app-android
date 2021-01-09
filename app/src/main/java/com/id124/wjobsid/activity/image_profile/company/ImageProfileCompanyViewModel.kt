package com.id124.wjobsid.activity.image_profile.company

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id124.wjobsid.model.company.CompanyResponse
import com.id124.wjobsid.service.CompanyApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ImageProfileCompanyViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: CompanyApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: CompanyApiService) {
        this@ImageProfileCompanyViewModel.service = service
    }

    fun serviceUpdateImageEngineer(cnId: Int, image: MultipartBody.Part? = null) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateCompanyImage(
                        cnId = cnId,
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

            if (response is CompanyResponse) {
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