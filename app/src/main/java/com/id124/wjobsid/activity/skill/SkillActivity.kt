package com.id124.wjobsid.activity.skill

import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.databinding.ActivitySkillBinding

class SkillActivity : BaseActivity<ActivitySkillBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_skill
        super.onCreate(savedInstanceState)

        setToolbarActionBar()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Skill"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ln_back -> {
                this@SkillActivity.finish()
            }
        }
    }
}