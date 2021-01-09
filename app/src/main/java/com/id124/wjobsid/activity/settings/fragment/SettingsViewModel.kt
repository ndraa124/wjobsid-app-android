package com.id124.wjobsid.activity.settings.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.company.CompanyResponse
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.service.AccountApiService
import com.id124.wjobsid.service.CompanyApiService
import com.id124.wjobsid.service.EngineerApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class SettingsViewModel : ViewModel(), CoroutineScope {
    private lateinit var serviceAccount: AccountApiService
    private lateinit var serviceEngineer: EngineerApiService
    private lateinit var serviceCompany: CompanyApiService

    val onSuccessLiveData = MutableLiveData<Boolean>()
    val onMessageLiveData = MutableLiveData<String>()
    val onFailLiveData = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(serviceAccount: AccountApiService, serviceEngineer: EngineerApiService, serviceCompany: CompanyApiService) {
        this@SettingsViewModel.serviceAccount = serviceAccount
        this@SettingsViewModel.serviceEngineer = serviceEngineer
        this@SettingsViewModel.serviceCompany = serviceCompany
    }

    fun serviceUpdateAccount(field: String, value: String, acId: Int) {
        val map: HashMap<String, String> = HashMap()
        map[field] = value

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceAccount.updateAccount(
                        acId = acId,
                        fields = map
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

            if (response is AccountResponse) {
                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }

    fun serviceUpdateEngineer(field: String, value: String, enId: Int) {
        val map: HashMap<String, String> = HashMap()
        map[field] = value

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceEngineer.updateEngineer(
                        enId = enId,
                        fields = map
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
                if (response.success) {
                    onSuccessLiveData.value = true
                    onMessageLiveData.value = response.message
                } else {
                    onFailLiveData.value = response.message
                }
            }
        }
    }

    fun serviceUpdateCompany(field: String, value: String, cnId: Int) {
        val map: HashMap<String, String> = HashMap()
        map[field] = value

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceCompany.updateCompany(
                        cnId = cnId,
                        fields = map
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