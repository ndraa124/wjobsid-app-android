package com.id124.wjobsid.activity.detail_profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.flexbox.FlexboxLayoutManager
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.fragment.experience.DetailProfileExperienceFragment
import com.id124.wjobsid.activity.detail_profile.fragment.portfolio.DetailProfilePortfolioFragment
import com.id124.wjobsid.activity.hire.HireActivity
import com.id124.wjobsid.activity.skill.adapter.ProfileSkillAdapter
import com.id124.wjobsid.base.BaseActivityCoroutine
import com.id124.wjobsid.databinding.ActivityProfileDetailBinding
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.model.skill.SkillModel
import com.id124.wjobsid.model.skill.SkillResponse
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.service.SkillApiService
import com.id124.wjobsid.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_profile_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileDetailActivity : BaseActivityCoroutine<ActivityProfileDetailBinding>(), View.OnClickListener {
    private var enId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_profile_detail
        super.onCreate(savedInstanceState)
        enId = intent.getIntExtra("en_id", 0)

        Log.d("msg", "ID ENNGINEER: $enId")

        if (sharedPref.getLevelUser() == 0) {
            bind.btnHire.visibility = View.GONE
        } else {
            bind.btnHire.visibility = View.VISIBLE
        }

        setToolbarActionBar()
        initViewPager()
        setSkillRecyclerView()

        setEngineer()
        setSkill()
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

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(1)
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
        bind.tabLayout.setupWithViewPager(view_pager)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(DetailProfilePortfolioFragment(enId!!), "Portfolio")
        adapter.addFrag(DetailProfileExperienceFragment(enId!!), "Experience")
        bind.viewPager.adapter = adapter
    }

    private fun setSkillRecyclerView() {
        bind.rvSkill.layoutManager = FlexboxLayoutManager(this@ProfileDetailActivity)

        val adapter = ProfileSkillAdapter()
        bind.rvSkill.adapter = adapter
    }

    private fun setEngineer() {
        bind.engineer = EngineerModel(
            enId = enId!!,
            acId = intent.getIntExtra("ac_id", 0),
            acName = intent.getStringExtra("ac_name")!!,
            enJobTitle = intent.getStringExtra("en_job_title"),
            enJobType = intent.getStringExtra("en_job_type"),
            enDomicile = intent.getStringExtra("en_domicile"),
            enDescription = intent.getStringExtra("en_description")
        )

        bind.imageUrl = ApiClient.BASE_URL_IMAGE + intent.getStringExtra("en_profile")
    }

    private fun setSkill() {
        val service = createApi<SkillApiService>(this@ProfileDetailActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.Main) {
                try {
                    service.getAllSkill(intent.getIntExtra("en_id", 0))
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