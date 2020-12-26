package com.id124.wjobsid.activity.main.fragment.profile.engineer_user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.flexbox.FlexboxLayoutManager
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseFragment
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.experience.ExperienceFragment
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.portfolio.PortfolioFragment
import com.id124.wjobsid.activity.settings.SettingsActivity
import com.id124.wjobsid.activity.skill.SkillActivity
import com.id124.wjobsid.activity.skill.adapter.ProfileSkillAdapter
import com.id124.wjobsid.databinding.FragmentProfileEngineerBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.model.skill.SkillModel
import com.id124.wjobsid.util.SharedPreference.Companion.AC_EMAIL
import com.id124.wjobsid.util.SharedPreference.Companion.AC_NAME
import com.id124.wjobsid.util.ViewPagerAdapter

class ProfileEngineerFragment : BaseFragment<FragmentProfileEngineerBinding>(), View.OnClickListener {
    private var skillModel = ArrayList<SkillModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_engineer
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContentViewEngineer()
        setSkillRecyclerView()

        bind.accountModel = AccountModel(
            acName = userDetail[AC_NAME],
            acEmail = userDetail[AC_EMAIL],
            acPhone = "082192089334"
        )
        bind.engineerModel = EngineerModel(
            enJobTitle = "Android Developer",
            enDomicile = "Manado, Sulawesi Utara",
            enJobType = "Freelance",
            enDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum erat orci, " +
                    "mollis nec gravida sed, ornare quis urna. Curabitur eu lacus fringilla, vestibulum risus at.",
            enProfile = "",
            acName = "",
            acId = "",
            enId = ""
        )
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
        dataSkill()

        val adapter = ProfileSkillAdapter(skillModel)
        bind.rvSkill.adapter = adapter

        adapter.setOnItemClickCallback(object: ProfileSkillAdapter.OnItemClickCallback {
            override fun onItemClick(data: SkillModel) {
                Toast.makeText(activity, "${data.sk_skill_name}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_engineer -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.btn_logout -> {
                logoutConf(activity)
            }
            R.id.iv_add_skill -> {
                startActivity(Intent(activity, SkillActivity::class.java))
            }
        }
    }

    private fun dataSkill() {
        skillModel = ArrayList()
        skillModel.add(SkillModel(sk_skill_name = "PHP"))
        skillModel.add(SkillModel(sk_skill_name = "Javascript"))
        skillModel.add(SkillModel(sk_skill_name = "Dart"))
        skillModel.add(SkillModel(sk_skill_name = "Kotlin"))
        skillModel.add(SkillModel(sk_skill_name = "Java"))
        skillModel.add(SkillModel(sk_skill_name = "HTML5"))
        skillModel.add(SkillModel(sk_skill_name = "CSS3"))
        skillModel.add(SkillModel(sk_skill_name = "C++"))
        skillModel.add(SkillModel(sk_skill_name = "C#"))
        skillModel.add(SkillModel(sk_skill_name = "C"))
        skillModel.add(SkillModel(sk_skill_name = "Node JS"))
        skillModel.add(SkillModel(sk_skill_name = "Express JS"))
        skillModel.add(SkillModel(sk_skill_name = "React JS"))
        skillModel.add(SkillModel(sk_skill_name = "Vue JS"))
        skillModel.add(SkillModel(sk_skill_name = "Angular JS"))
        skillModel.add(SkillModel(sk_skill_name = "CodeIgniter"))
        skillModel.add(SkillModel(sk_skill_name = "Laravel"))
        skillModel.add(SkillModel(sk_skill_name = "Spring"))
        skillModel.add(SkillModel(sk_skill_name = "Golang"))
        skillModel.add(SkillModel(sk_skill_name = "Python"))
        skillModel.add(SkillModel(sk_skill_name = "Flutter"))
    }

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(0)
    }
}