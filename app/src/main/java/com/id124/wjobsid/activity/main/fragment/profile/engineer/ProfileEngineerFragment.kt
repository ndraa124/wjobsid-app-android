package com.id124.wjobsid.activity.main.fragment.profile.engineer

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.flexbox.FlexboxLayoutManager
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.image_profile.engineer.ImageProfileEngineerActivity
import com.id124.wjobsid.activity.main.fragment.profile.engineer.fragment_engineer_user.experience.ExperienceFragment
import com.id124.wjobsid.activity.main.fragment.profile.engineer.fragment_engineer_user.portfolio.PortfolioFragment
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
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE_DEFAULT_PROFILE_2
import com.id124.wjobsid.util.ViewPagerAdapter

class ProfileEngineerFragment : BaseFragmentCoroutine<FragmentProfileEngineerBinding>(), ProfileEngineerContract.View, View.OnClickListener {
    private var presenter: ProfileEngineerPresenter? = null

    companion object {
        const val INTENT_ADD = 100
        const val INTENT_EDIT = 200
        const val INTENT_EDIT_IMAGE = 300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_engineer
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProfileEngineerPresenter(
            serviceAccount = createApi(activity),
            serviceEngineer = createApi(activity),
            serviceSkill = createApi(activity)
        )

        setContentViewEngineer()
        setSkillRecyclerView()
        setupDataProfile()
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
            presenter?.callServiceSkill(sharedPref.getIdEngineer())
        } else if (requestCode == INTENT_EDIT && resultCode == RESULT_OK) {
            presenter?.callServiceSkill(sharedPref.getIdEngineer())
        } else if (requestCode == INTENT_EDIT_IMAGE && resultCode == RESULT_OK) {
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

    override fun onResultSuccessEngineer(data: EngineerResponse.EngineerItem) {
        bind.tvDescription.setShowingLine(2)
        bind.tvDescription.addShowMoreText("read more")
        bind.tvDescription.addShowLessText("less")
        bind.tvDescription.setShowMoreColor(Color.BLUE)
        bind.tvDescription.setShowLessTextColor(Color.BLUE)

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

        if (data.enProfile != null) {
            bind.imageUrl = BASE_URL_IMAGE + data.enProfile
        } else {
            bind.imageUrl = BASE_URL_IMAGE_DEFAULT_PROFILE_2
        }

        bind.ivImageProfile.setOnClickListener {
            val intent = Intent(activity, ImageProfileEngineerActivity::class.java)
            intent.putExtra("en_id", data.enId)
            intent.putExtra("en_profile", data.enProfile)
            startActivityForResult(intent, INTENT_EDIT_IMAGE)
        }
    }

    override fun onResultSuccessSkill(list: List<SkillModel>) {
        (bind.rvSkill.adapter as ProfileSkillAdapter).addList(list)
        bind.flSkill.visibility = View.VISIBLE
        bind.tvDataNotFound.visibility = View.GONE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            bind.flSkill.visibility = View.GONE
            bind.tvDataNotFound.visibility = View.VISIBLE
            bind.dataNotFound = message
        }
    }

    override fun showLoading() {
        bind.shimmerViewContainer.visibility = View.VISIBLE
        bind.progressBar.visibility = View.VISIBLE

        bind.cvIdentity.visibility = View.GONE
        bind.cvContact.visibility = View.GONE
        bind.cvSkill.visibility = View.GONE
        bind.cvCurriculumVitae.visibility = View.GONE
        bind.flSkill.visibility = View.GONE
    }

    override fun hideLoading() {
        bind.cvIdentity.visibility = View.VISIBLE
        bind.cvContact.visibility = View.VISIBLE
        bind.cvSkill.visibility = View.VISIBLE
        bind.cvCurriculumVitae.visibility = View.VISIBLE

        bind.shimmerViewContainer.stopShimmerAnimation()
        bind.shimmerViewContainer.visibility = View.GONE
        bind.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(0)
    }

    override fun onResume() {
        super.onResume()
        presenter?.callServiceAccount(sharedPref.getIdAccount())
        presenter?.callServiceEngineer(sharedPref.getIdAccount())
        presenter?.callServiceSkill(sharedPref.getIdEngineer())
    }

    override fun onPause() {
        super.onPause()
        bind.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
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
        bind.ivImageProfile.setOnClickListener(this@ProfileEngineerFragment)
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

    private fun setupDataProfile() {
        bind.shimmerViewContainer.startShimmerAnimation()

        presenter?.bindToView(this@ProfileEngineerFragment)
        presenter?.callServiceAccount(sharedPref.getIdAccount())
        presenter?.callServiceEngineer(sharedPref.getIdAccount())
        presenter?.callServiceSkill(sharedPref.getIdEngineer())
    }
}