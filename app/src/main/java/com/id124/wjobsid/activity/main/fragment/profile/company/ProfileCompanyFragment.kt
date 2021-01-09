package com.id124.wjobsid.activity.main.fragment.profile.company

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.image_profile.company.ImageProfileCompanyActivity
import com.id124.wjobsid.activity.settings.SettingsActivity
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentProfileCompanyBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.company.CompanyModel
import com.id124.wjobsid.model.company.CompanyResponse
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import kotlinx.android.synthetic.main.fragment_profile_company.view.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.view.*

class ProfileCompanyFragment : BaseFragmentCoroutine<FragmentProfileCompanyBinding>(), ProfileCompanyContract.View, View.OnClickListener {
    private var presenter: ProfileCompanyPresenter? = null

    companion object {
        const val INTENT_EDIT_IMAGE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_company
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProfileCompanyPresenter(
            serviceAccount = createApi(activity),
            serviceCompany = createApi(activity)
        )

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INTENT_EDIT_IMAGE && resultCode == Activity.RESULT_OK) {
            presenter?.callServiceAccount(sharedPref.getIdAccount())
        }
    }

    override fun onResultSuccessAccount(data: AccountResponse.AccountItem) {
        bind.accountModel = AccountModel(
            acName = data.acName,
            acEmail = data.acEmail,
            acPhone = data.acPhone
        )
    }

    override fun onResultSuccessCompany(data: CompanyResponse.CompanyItem) {
        bind.companyModel = CompanyModel(
            cn_company = data.cnCompany,
            cn_position = data.cnPosition,
            cn_field = data.cnField,
            cn_city = data.cnCity,
            cn_description = data.cnDescription,
            cn_instagram = data.cnInstagram,
            cn_linkedin = data.cnLinkedin
        )

        if (data.cnProfile != null) {
            bind.imageUrl = BASE_URL_IMAGE + data.cnProfile
        } else {
            bind.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE_2
        }

        bind.ivImageProfile.setOnClickListener {
            val intent = Intent(activity, ImageProfileCompanyActivity::class.java)
            intent.putExtra("cn_id", data.cnId)
            intent.putExtra("cn_profile", data.cnProfile)
            startActivityForResult(intent, INTENT_EDIT_IMAGE)
        }
    }

    override fun onResultFail(message: String) {
        noticeToast(message)
    }

    override fun showLoading() {
        bind.cvIdentity.visibility = View.GONE
        bind.cvContact.visibility = View.GONE
        bind.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        bind.progressBar.visibility = View.GONE
        bind.cvIdentity.visibility = View.VISIBLE
        bind.cvContact.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(0)

        presenter?.bindToView(this@ProfileCompanyFragment)
        presenter?.callServiceAccount(sharedPref.getIdAccount())
        presenter?.callServiceCompany(sharedPref.getIdAccount())
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setContentViewCompany() {
        bind.btnEditCompany.setOnClickListener(this@ProfileCompanyFragment)
        bind.btnLogout.setOnClickListener(this@ProfileCompanyFragment)
        bind.ivImageProfile.setOnClickListener(this@ProfileCompanyFragment)
    }
}