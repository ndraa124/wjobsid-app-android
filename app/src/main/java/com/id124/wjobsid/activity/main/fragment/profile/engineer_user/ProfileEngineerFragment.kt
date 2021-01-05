package com.id124.wjobsid.activity.main.fragment.profile.engineer_user

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.flexbox.FlexboxLayoutManager
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.experience.ExperienceFragment
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.portfolio.PortfolioFragment
import com.id124.wjobsid.activity.settings.SettingsActivity
import com.id124.wjobsid.activity.skill.SkillActivity
import com.id124.wjobsid.activity.skill.adapter.ProfileSkillAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentProfileEngineerBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.model.skill.SkillModel
import com.id124.wjobsid.model.skill.SkillResponse
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.service.AccountApiService
import com.id124.wjobsid.service.EngineerApiService
import com.id124.wjobsid.service.SkillApiService
import com.id124.wjobsid.util.ViewPagerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileEngineerFragment : BaseFragmentCoroutine<FragmentProfileEngineerBinding>(), View.OnClickListener {
    companion object {
        const val INTENT_ADD = 100
        const val INTENT_EDIT = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_engineer
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContentViewEngineer()
        setSkillRecyclerView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_engineer -> {
                intents<SettingsActivity>(activity)
            }
            R.id.btn_logout -> {
                logoutConf(activity)
            }
            R.id.iv_add_skill -> {
                intentsResults<SkillActivity>(activity, INTENT_ADD)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INTENT_ADD && resultCode == RESULT_OK) {
            setSkill()
        } else if (requestCode == INTENT_EDIT && resultCode == RESULT_OK) {
            setSkill()
        }
    }

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(0)

        setAccount()
        setEngineer()
        setSkill()
    }

    override fun onPause() {
        super.onPause()

        setAccount()
        setEngineer()
        setSkill()
    }

    private fun setContentViewEngineer() {
        bind.tabLayout.setupWithViewPager(bind.viewPager)
        val adapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFrag(PortfolioFragment(), "Portfolio")
        adapter.addFrag(ExperienceFragment(), "Experience")
        bind.viewPager.adapter = adapter

        bind.btnEditEngineer.setOnClickListener(this@ProfileEngineerFragment)
        bind.btnLogout.setOnClickListener(this@ProfileEngineerFragment)
        bind.ivAddSkill.setOnClickListener(this@ProfileEngineerFragment)
    }

    private fun setSkillRecyclerView() {
        bind.rvSkill.layoutManager = FlexboxLayoutManager(context)

        val adapter = ProfileSkillAdapter()
        bind.rvSkill.adapter = adapter

        adapter.setOnItemClickCallback(object: ProfileSkillAdapter.OnItemClickCallback {
            override fun onItemClick(data: SkillModel) {
                val intent = Intent(activity, SkillActivity::class.java)
                intent.putExtra("sk_id", data.sk_id)
                intent.putExtra("sk_skill_name", data.sk_skill_name)
                startActivityForResult(intent, INTENT_EDIT)
            }
        })
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

    private fun setEngineer() {
        coroutineScope.launch {
            val service = createApi<EngineerApiService>(activity)

            val response = withContext(Dispatchers.IO) {
                try {
                    service.getDetailEngineer(sharedPref.getIdAccount())
                } catch (t: Throwable) {
                    Log.d("msg", "${t.message}")
                }
            }

            if (response is EngineerResponse) {
                val data = response.data[0]

                bind.engineerModel = EngineerModel(
                    enJobTitle = data.enJobTitle,
                    enDomicile = data.enDomicile,
                    enJobType = data.enJobType,
                    enDescription = data.enDescription,
                    enProfile = data.enProfile,
                    acName = data.acName,
                    acId = data.acId,
                    enId = data.enId
                )

                bind.imageUrl = BASE_URL_IMAGE + data.enProfile
            }
        }
    }

    private fun setSkill() {
        val service = createApi<SkillApiService>(activity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.Main) {
                try {
                    service.getAllSkill(sharedPref.getIdEngineer())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is SkillResponse) {
                val list = response.data.map {
                    SkillModel(
                        sk_id = it.sk_id,
                        sk_skill_name = it.skSkillName
                    )
                }

                (bind.rvSkill.adapter as ProfileSkillAdapter).addList(list)
            }
        }
    }
}