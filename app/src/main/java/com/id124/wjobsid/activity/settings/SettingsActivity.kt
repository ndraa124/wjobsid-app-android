package com.id124.wjobsid.activity.settings

import android.os.Bundle
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.settings.fragment.SettingsFragment
import com.id124.wjobsid.base.BaseActivity
import com.id124.wjobsid.databinding.ActivitySettingsEngineerBinding

class SettingsActivity : BaseActivity<ActivitySettingsEngineerBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_settings_engineer
        super.onCreate(savedInstanceState)
        setToolbarActionBar()

        val ids: Int = if (sharedPref.getLevelUser() == 0) {
            sharedPref.getIdEngineer()
        } else {
            sharedPref.getIdCompany()
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment(ids, sharedPref.getIdAccount(), sharedPref.getLevelUser()))
                .commit()
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Profile"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}