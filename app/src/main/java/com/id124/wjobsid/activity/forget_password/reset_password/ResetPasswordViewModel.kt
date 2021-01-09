package com.id124.wjobsid.activity.forget_password.reset_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.service.AccountApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ResetPasswordViewModel : ViewModel(), CoroutineScope {
    private lateinit var serviceAccount: AccountApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(serviceAccount: AccountApiService) {
        this@ResetPasswordViewModel.serviceAccount = serviceAccount
    }

    fun serviceUpdate(acId: Int, acPassword: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    serviceAccount.resetPassword(
                        acId = acId,
                        acPassword = acPassword
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        isLoadingLiveData.value = false

                        when {
                            e.code() == 404 -> {
                                onFailLiveData.value = "Data not found!"
                            }
                            e.code() == 400 -> {
                                onFailLiveData.value = "Fail to reset password!"
                            }
                            else -> {
                                onFailLiveData.value = "Internal Server Error!"
                            }
                        }
                    }
                }
            }

            if (response is AccountResponse) {
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