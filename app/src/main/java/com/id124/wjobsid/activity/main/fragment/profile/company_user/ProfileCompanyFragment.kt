package com.id124.wjobsid.activity.main.fragment.profile.company_user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.settings.SettingsActivity
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentProfileCompanyBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.company.CompanyModel
import com.id124.wjobsid.model.company.CompanyResponse
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.service.AccountApiService
import com.id124.wjobsid.service.CompanyApiService
import kotlinx.android.synthetic.main.fragment_profile_company.view.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileCompanyFragment : BaseFragmentCoroutine<FragmentProfileCompanyBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_company
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContentViewCompany()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_company -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.btn_logout -> {
                logoutConf(activity)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(0)

        setAccount()
        setCompany()
    }

    override fun onPause() {
        super.onPause()

        setAccount()
        setCompany()
    }

    private fun setContentViewCompany() {
        bind.btnEditCompany.setOnClickListener(this@ProfileCompanyFragment)
        bind.btnLogout.setOnClickListener(this@ProfileCompanyFragment)
    }

    private fun setAccount() {
        coroutineScope.launch {
            val service = createApi<AccountApiService>(activity)

            val response = withContext(Dispatchers.IO) {
                try {
                    service.detailAccount(sharedPref.getIdAccount())
                } catch (t: Throwable) {
                    Log.d("msg", "${t.message}")
                }
            }

            if (response is AccountResponse) {
                val data = response.data[0]

                bind.accountModel = AccountModel(
                    acName = data.acName,
                    acEmail = data.acEmail,
                    acPhone = data.acPhone
                )
            }
        }
    }

    private fun setCompany() {
        coroutineScope.launch {
            val service = createApi<CompanyApiService>(activity)

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getDetailCompany(sharedPref.getIdAccount())
                } catch (t: Throwable) {
                    Log.d("msg", "${t.message}")
                }
            }

            if (response is CompanyResponse) {
                val data = response.data[0]

                bind.companyModel = CompanyModel(
                    cn_company = data.cnCompany,
                    cn_position = data.cnPosition,
                    cn_field = data.cnField,
                    cn_city = data.cnCity,
                    cn_description = data.cnDescription,
                    cn_instagram = data.cnInstagram,
                    cn_linkedin = data.cnLinkedin
                )

                bind.imageUrl = BASE_URL_IMAGE + data.cnProfile
            }
        }
    }
}