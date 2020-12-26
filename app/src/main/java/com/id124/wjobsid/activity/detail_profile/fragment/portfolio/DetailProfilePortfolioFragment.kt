package com.id124.wjobsid.activity.detail_profile.fragment.portfolio

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.base.BaseFragment
import com.id124.wjobsid.activity.portfolio.PortfolioActivity
import com.id124.wjobsid.activity.detail_profile.fragment.portfolio.adapter.ProfileDetailPortfolioAdapter
import com.id124.wjobsid.databinding.FragmentPortfolioBinding
import com.id124.wjobsid.model.portfolio.PortfolioModel
import kotlinx.android.synthetic.main.fragment_portfolio.view.*

class DetailProfilePortfolioFragment : BaseFragment<FragmentPortfolioBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_portfolio
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (sharedPref.getInDetail() == 0) {
            bind.btnAddPortfolio.visibility = View.VISIBLE
        } else {
            bind.btnAddPortfolio.visibility = View.GONE
        }

        setupPortfolioRecyclerView()
        bind.btnAddPortfolio.setOnClickListener(this@DetailProfilePortfolioFragment)
    }

    private fun setupPortfolioRecyclerView() {
        bind.rvPortfolio.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val portfolioModel: ArrayList<PortfolioModel> = ArrayList()
        portfolioModel.add(
            PortfolioModel(
            pr_image = "http://192.168.43.123:3000/images/IMG-1605622615798.png"
        )
        )
        portfolioModel.add(
            PortfolioModel(
            pr_image = "http://192.168.43.123:3000/images/IMG-1605622381179.png"
        )
        )
        portfolioModel.add(
            PortfolioModel(
            pr_image = "http://192.168.43.123:3000/images/IMG-1605617120374.png"
        )
        )
        portfolioModel.add(
            PortfolioModel(
            pr_image = "http://192.168.43.123:3000/images/IMG-1605622615798.png"
        )
        )
        portfolioModel.add(
            PortfolioModel(
            pr_image = "http://192.168.43.123:3000/images/IMG-1605622381179.png"
        )
        )
        portfolioModel.add(
            PortfolioModel(
            pr_image = "http://192.168.43.123:3000/images/IMG-1605617120374.png"
        )
        )

        bind.rvPortfolio.adapter = ProfileDetailPortfolioAdapter(portfolioModel)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_portfolio -> {
                intents<PortfolioActivity>(activity)
            }
        }
    }
}