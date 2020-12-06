package com.id124.wjobsid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.id124.wjobsid.R

class PortfolioActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)

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