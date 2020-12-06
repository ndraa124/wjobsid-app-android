package com.id124.wjobsid.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.PortfolioActivity
import kotlinx.android.synthetic.main.fragment_portfolio.view.*

class PortfolioFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_portfolio, container, false)


        view.btn_add_portfolio.setOnClickListener(this@PortfolioFragment)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_portfolio -> {
                startActivity(Intent(activity, PortfolioActivity::class.java))
            }
        }
    }
}