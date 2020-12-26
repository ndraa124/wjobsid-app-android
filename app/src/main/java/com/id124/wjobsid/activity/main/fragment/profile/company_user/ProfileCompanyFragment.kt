package com.id124.wjobsid.activity.main.fragment.profile.company_user

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseFragment
import com.id124.wjobsid.activity.settings.SettingsActivity
import com.id124.wjobsid.databinding.FragmentProfileCompanyBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.company.CompanyModel
import com.id124.wjobsid.util.SharedPreference
import kotlinx.android.synthetic.main.fragment_profile_company.view.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.view.*

class ProfileCompanyFragment : BaseFragment<FragmentProfileCompanyBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_company
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContentViewCompany()

        bind.accountModel = AccountModel(
            acName = userDetail[SharedPreference.AC_NAME],
            acEmail = userDetail[SharedPreference.AC_EMAIL],
            acPhone = "082192089334"
        )
        bind.companyModel = CompanyModel(
            cn_company = "PT. Technology Associate",
            cn_position = "Chief Technology Officer",
            cn_city = "Manado, Sulawesi Utara",
            cn_field = "Software Developer",
            cn_description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum erat orci, " +
                    "mollis nec gravida sed, ornare quis urna. Curabitur eu lacus fringilla, vestibulum risus at.",
            cn_instagram = "@teckassociate",
            cn_linkedin = "@teckassociate"
        )
    }

    private fun setContentViewCompany() {
        bind.btnEditCompany.setOnClickListener(this@ProfileCompanyFragment)
        bind.btnLogout.setOnClickListener(this@ProfileCompanyFragment)
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
    }
}