package com.id124.wjobsid.activity.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.id124.wjobsid.*
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.activity.main.fragment.HomeFragment
import com.id124.wjobsid.activity.main.fragment.ProjectFragment
import com.id124.wjobsid.activity.main.fragment.SearchFragment
import com.id124.wjobsid.activity.main.fragment.profile.ProfileCompanyFragment
import com.id124.wjobsid.activity.main.fragment.profile.ProfileEngineerFragment
import com.id124.wjobsid.databinding.ActivityMainBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>(), ChipNavigationBar.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main
        super.onCreate(savedInstanceState)
        sharedPref.checkIsLogin()
        getFragment(HomeFragment())

        bnv_main.setItemSelected(R.id.menu_home)
        bnv_main.setOnItemSelectedListener(this@MainActivity)
    }

    override fun onItemSelected(id: Int) {
        when (id) {
            R.id.menu_home -> {
                getFragment(HomeFragment())
            }
            R.id.menu_search -> {
                getFragment(SearchFragment())
            }
            R.id.menu_project -> {
                getFragment(ProjectFragment())
            }
            R.id.menu_profile -> {
                sharedPref.createInDetail(0)

                if (sharedPref.getLevelUser() == 0) {
                    getFragment(ProfileEngineerFragment())
                } else {
                    getFragment(ProfileCompanyFragment())
                }
            }
        }
    }

    private fun getFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.fl_main, fragment)
            .commit()
    }

    override fun onBackPressed() {
        this@MainActivity.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}