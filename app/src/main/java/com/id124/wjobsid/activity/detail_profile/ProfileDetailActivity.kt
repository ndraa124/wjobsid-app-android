package com.id124.wjobsid.activity.detail_profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.flexbox.FlexboxLayoutManager
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.adapter.ProfileDetailSkillAdapter
import com.id124.wjobsid.activity.detail_profile.fragment.experience.DetailProfileExperienceFragment
import com.id124.wjobsid.activity.detail_profile.fragment.portfolio.DetailProfilePortfolioFragment
import com.id124.wjobsid.activity.hire.HireActivity
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityDetailProfileBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.account.AccountResponse
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.model.skill.SkillModel
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.util.ViewPagerAdapter

class ProfileDetailActivity : BaseActivityCoroutine<ActivityDetailProfileBinding>(), ProfileDetailContract.View, View.OnClickListener {
    private var presenter: ProfileDetailPresenter? = null
    private var enId: Int? = 0
    private var acId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_profile
        super.onCreate(savedInstanceState)

        enId = intent.getIntExtra("en_id", 0)
        acId = intent.getIntExtra("ac_id", 0)

        presenter = ProfileDetailPresenter(
            serviceAccount = createApi(this@ProfileDetailActivity),
            serviceEngineer = createApi(this@ProfileDetailActivity),
            serviceSkill = createApi(this@ProfileDetailActivity),
            serviceHire = createApi(this@ProfileDetailActivity),
        )

        if (sharedPref.getLevelUser() == 0) {
            bind.btnHire.visibility = View.GONE
        } else {
            bind.btnHire.visibility = View.VISIBLE
        }

        setToolbarActionBar()
        initViewPager()
        setSkillRecyclerView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_hire -> {
                val intent = Intent(this@ProfileDetailActivity, HireActivity::class.java)
                intent.putExtra("en_id", enId)
                startActivity(intent)
            }
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
            bind.imageUrl = ApiClient.BASE_URL_IMAGE + data.enProfile
        } else {
            bind.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE_2
        }

    }

    override fun onResultSuccessSkill(list: List<SkillModel>) {
        (bind.rvSkill.adapter as ProfileDetailSkillAdapter).addList(list)
        bind.flSkill.visibility = View.VISIBLE
    }

    override fun onResultSuccessHire() {
        bind.btnHire.visibility = View.GONE
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

    override fun onResultFailHire(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            if (sharedPref.getLevelUser() == 0) {
                bind.btnHire.visibility = View.GONE
            } else {
                bind.btnHire.visibility = View.VISIBLE
            }
        }
    }

    override fun showLoading() {
        bind.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        bind.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(1)

        presenter?.bindToView(this@ProfileDetailActivity)
        presenter?.callServiceAccount(acId = acId)
        presenter?.callServiceEngineer(acId = acId)
        presenter?.callServiceSkill(enId = enId)
        presenter?.callServiceIsHire(enId = enId)
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Detail"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewPager() {
        bind.tabLayout.setupWithViewPager(bind.viewPager)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(DetailProfilePortfolioFragment(enId!!), "Portfolio")
        adapter.addFrag(DetailProfileExperienceFragment(enId!!), "Experience")
        bind.viewPager.adapter = adapter
    }

    private fun setSkillRecyclerView() {
        bind.rvSkill.layoutManager = FlexboxLayoutManager(this@ProfileDetailActivity)

        val adapter = ProfileDetailSkillAdapter()
        bind.rvSkill.adapter = adapter
    }
}