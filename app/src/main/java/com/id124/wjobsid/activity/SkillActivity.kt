package com.id124.wjobsid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R

class SkillActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = "Skill"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ln_back -> {
                this@SkillActivity.finish()
            }
        }
    }
}