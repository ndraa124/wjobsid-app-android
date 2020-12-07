package com.id124.wjobsid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.id124.wjobsid.*
import com.id124.wjobsid.fragment.HomeFragment
import com.id124.wjobsid.fragment.ProfileFragment
import com.id124.wjobsid.fragment.ProjectFragment
import com.id124.wjobsid.fragment.SearchFragment
import com.id124.wjobsid.helper.SharedPreference
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ChipNavigationBar.OnItemSelectedListener {
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = SharedPreference(this@MainActivity)
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
                sharedPreference.setDetail(0)
                getFragment(ProfileFragment())
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
}