package com.id124.wjobsid.activity.image_profile.engineer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.service.EngineerApiService
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ImageProfileEngineerViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: EngineerApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: EngineerApiService) {
        this@ImageProfileEngineerViewModel.service = service
    }

    fun serviceUpdateImageEngineer(enId: Int, image: MultipartBody.Part? = null) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateEngineerImage(
                        enId = enId,
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

            if (response is EngineerResponse) {
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