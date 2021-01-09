package com.id124.wjobsid.activity.main.fragment.profile.company

import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.company.CompanyResponse
import com.id124.wjobsid.service.AccountApiService
import com.id124.wjobsid.service.CompanyApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class ProfileCompanyPresenter(
    private val serviceAccount: AccountApiService,
    private val serviceCompany: CompanyApiService
) : CoroutineScope,
    ProfileCompanyContract.Presenter {

    private var view: ProfileCompanyContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    override fun bindToView(view: ProfileCompanyContract.View) {
        this@ProfileCompanyPresenter.view = view
    }

    override fun unbind() {
        this@ProfileCompanyPresenter.view = null
    }

    override fun callServiceAccount(acId: Int?) {
        view?.showLoading()

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceAccount.detailAccount(acId = acId!!)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()
                        view?.onResultFail(e.message())
                    }
                }
            }

            if (response is AccountResponse) {
                view?.hideLoading()

                if (response.success) {
                    val data = response.data[0]

                    view?.onResultSuccessAccount(data)
                }
            }
        }
    }

    override fun callServiceCompany(acId: Int?) {
        view?.showLoading()

        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceCompany.getDetailCompany(acId = acId!!)
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        view?.hideLoading()
                        view?.onResultFail(e.message())
                    }
                }
            }

            if (response is CompanyResponse) {
                view?.hideLoading()

                if (response.success) {
                    val data = response.data[0]

                    view?.onResultSuccessCompany(data)
                }
            }
        }
    }
}