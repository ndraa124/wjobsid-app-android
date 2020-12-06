package com.id124.wjobsid.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.id124.wjobsid.*
import com.id124.wjobsid.fragment.HomeFragment
import com.id124.wjobsid.fragment.ProfileFragment
import com.id124.wjobsid.fragment.ProjectFragment
import com.id124.wjobsid.fragment.SearchFragment
import com.id124.wjobsid.helper.SharedPreference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = SharedPreference(this@MainActivity)
        getFragment(HomeFragment())

        bnv_main.setOnNavigationItemSelectedListener(this@MainActivity)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.menu_home -> {
                fragment = HomeFragment()
            }
            R.id.menu_search -> {
                fragment = SearchFragment()
            }
            R.id.menu_project -> {
                fragment = ProjectFragment()
            }
            R.id.menu_profile -> {
                sharedPreference.setDetail(0)
                fragment = ProfileFragment()
            }
        }

        return getFragment(fragment)
    }

    private fun getFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fl_main, fragment)
                .commit()

            return true
        }

        return false
    }
}