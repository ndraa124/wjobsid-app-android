package com.id124.wjobsid.activity.hire

import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.databinding.ActivityHireBinding

class HireActivity : BaseActivity<ActivityHireBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_hire
        super.onCreate(savedInstanceState)

        setToolbarActionBar()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(bind.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Hire"
        bind.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_process_hire -> {

            }
            R.id.ln_back -> {
                this@HireActivity.finish()
            }
        }
    }
}