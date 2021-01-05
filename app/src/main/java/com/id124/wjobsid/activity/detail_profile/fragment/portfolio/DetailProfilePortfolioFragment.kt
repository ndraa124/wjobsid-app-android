package com.id124.wjobsid.activity.detail_profile.fragment.portfolio

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.fragment.portfolio.adapter.ProfileDetailPortfolioAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentPortfolioBinding
import com.id124.wjobsid.model.portfolio.PortfolioModel
import com.id124.wjobsid.model.portfolio.PortfolioResponse
import com.id124.wjobsid.service.PortfolioApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailProfilePortfolioFragment(private val enId: Int) : BaseFragmentCoroutine<FragmentPortfolioBinding>() {
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
        setPortfolio()
    }

    private fun setupPortfolioRecyclerView() {
        bind.rvPortfolio.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ProfileDetailPortfolioAdapter()
        bind.rvPortfolio.adapter = adapter
    }

    private fun setPortfolio() {
        val service = createApi<PortfolioApiService>(activity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllPortfolio(enId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is PortfolioResponse) {
                val list = response.data.map {
                    PortfolioModel(
                        pr_id = it.prId,
                        en_id = it.enId,
                        pr_app = it.prApp,
                        pr_description = it.prDescription,
                        pr_link_pub = it.prLinkPub,
                        pr_link_repo = it.prLinkRepo,
                        pr_work_place = it.prWorkPlace,
                        pr_type = it.prType,
                        pr_image = it.prImage
                    )
                }

                (bind.rvPortfolio.adapter as ProfileDetailPortfolioAdapter).addList(list)
            }
        }
    }
}