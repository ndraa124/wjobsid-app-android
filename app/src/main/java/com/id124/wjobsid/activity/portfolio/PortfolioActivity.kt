package com.id124.wjobsid.activity.portfolio

import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.databinding.ActivityPortfolioBinding

class PortfolioActivity : BaseActivity<ActivityPortfolioBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_portfolio
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = "Portfolio"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_portfolio -> {

            }
            R.id.ln_back -> {
                this@PortfolioActivity.finish()
            }
        }
    }
}