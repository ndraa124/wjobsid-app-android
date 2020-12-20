package com.id124.wjobsid.activity.main.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.flexbox.FlexboxLayoutManager
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseFragment
import com.id124.wjobsid.activity.profile.fragment.ExperienceFragment
import com.id124.wjobsid.activity.profile.fragment.PortfolioFragment
import com.id124.wjobsid.activity.settings.SettingsActivity
import com.id124.wjobsid.activity.skill.SkillActivity
import com.id124.wjobsid.activity.skill.adapter.ProfileSkillAdapter
import com.id124.wjobsid.databinding.FragmentProfileEngineerBinding
import com.id124.wjobsid.model.AccountModel
import com.id124.wjobsid.model.EngineerModel
import com.id124.wjobsid.model.SkillModel
import com.id124.wjobsid.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_profile_company.view.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.view.*

class ProfileEngineerFragment : BaseFragment<FragmentProfileEngineerBinding>(), View.OnClickListener {
    private var skillModel = ArrayList<SkillModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_engineer
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContentViewEngineer(view)
        setSkillRecyclerView()

        bind.accountModel = AccountModel(ac_name = userDetail["AC_NAME"])
        bind.engineerModel = EngineerModel(
            en_job_title = "Android Developer",
            en_domicile = "Manado, Sulawesi Utara",
            en_job_type = "Freelance",
            en_description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum erat orci, " +
                    "mollis nec gravida sed, ornare quis urna. Curabitur eu lacus fringilla, vestibulum risus at."
        )
    }

    private fun setContentViewEngineer(view: View) {
        bind.tabLayout.setupWithViewPager(view.view_pager)
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

    override fun onStart() {
        super.onStart()
        sharedPref.createInDetail(0)
    }
}