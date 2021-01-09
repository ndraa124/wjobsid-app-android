package com.id124.wjobsid.activity.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id124.wjobsid.model.account.SignUpResponse
import com.id124.wjobsid.service.AccountApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class SignUpViewModel : ViewModel(), CoroutineScope {
    private lateinit var service: AccountApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()
    val isLoadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: AccountApiService) {
        this@SignUpViewModel.service = service
    }

    fun serviceEngineerApi(acName: String, acEmail: String, acPhone: String, acPassword: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.signUpEngineerAccount(
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone,
                        acPassword = acPassword,
                        acLevel = 0
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 400 -> {
                                onFailLiveData.value = "Email has registered!"
                            }
                            else -> {
                                onFailLiveData.value = "Fail to registration! Please try again later!"
                            }
                        }
                    }
                }
            }

            if (response is SignUpResponse) {
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

    fun serviceCompanyApi(acName: String, acEmail: String, acPhone: String, acPassword: String, cnCompany: String, cnPosition: String) {
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    service.signUpCompanyAccount(
                        acName = acName,
                        acEmail = acEmail,
                        acPhone = acPhone,
                        acPassword = acPassword,
                        acLevel = 1,
                        cnCompany = cnCompany,
                        cnPosition = cnPosition,
                    )
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onSuccessLiveData.value = false

                        when {
                            e.code() == 400 -> {
                                onFailLiveData.value = "Email has registered!"
                            }
                            else -> {
                                onFailLiveData.value = "Fail to registration! Please try again later!"
                            }
                        }
                    }
                }
            }

            if (response is SignUpResponse) {
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