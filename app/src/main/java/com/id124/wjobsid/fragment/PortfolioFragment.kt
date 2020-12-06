package com.id124.wjobsid.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.PortfolioActivity
import com.id124.wjobsid.helper.SharedPreference
import kotlinx.android.synthetic.main.fragment_portfolio.view.*

class PortfolioFragment : Fragment(), View.OnClickListener {
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreference = activity?.let { SharedPreference(it) }!!
        val view: View = inflater.inflate(R.layout.fragment_portfolio, container, false)

        if (sharedPreference.getDetail() == 0) {
            view.btn_add_portfolio.visibility = View.VISIBLE
        } else {
            view.btn_add_portfolio.visibility = View.GONE
        }

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