package com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.portfolio

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.ProfileEngineerFragment
import com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.portfolio.adapter.ProfilePortfolioAdapter
import com.id124.wjobsid.activity.portfolio.PortfolioActivity
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentPortfolioBinding
import com.id124.wjobsid.model.portfolio.PortfolioModel
import com.id124.wjobsid.model.portfolio.PortfolioResponse
import com.id124.wjobsid.service.PortfolioApiService
import kotlinx.android.synthetic.main.fragment_portfolio.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PortfolioFragment : BaseFragmentCoroutine<FragmentPortfolioBinding>(), View.OnClickListener {
    companion object {
        const val INTENT_ADD = 100
        const val INTENT_EDIT = 200
    }

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
        bind.btnAddPortfolio.setOnClickListener(this@PortfolioFragment)

        setPortfolio()
    }

    private fun setupPortfolioRecyclerView() {
        bind.rvPortfolio.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ProfilePortfolioAdapter()
        bind.rvPortfolio.adapter = adapter

        adapter.setOnItemClickCallback(object : ProfilePortfolioAdapter.OnItemClickCallback {
            override fun onItemClick(data: PortfolioModel) {
                val intent = Intent(activity, PortfolioActivity::class.java)
                intent.putExtra("pr_id", data.pr_id)
                intent.putExtra("en_id", data.en_id)
                intent.putExtra("pr_app", data.pr_app)
                intent.putExtra("pr_description", data.pr_description)
                intent.putExtra("pr_link_pub", data.pr_link_pub)
                intent.putExtra("pr_link_repo", data.pr_link_repo)
                intent.putExtra("pr_work_place", data.pr_work_place)
                intent.putExtra("pr_type", data.pr_type)
                intent.putExtra("pr_image", data.pr_image)
                startActivityForResult(intent, INTENT_EDIT)
            }
        })
    }

    private fun setPortfolio() {
        val service = createApi<PortfolioApiService>(activity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllPortfolio(sharedPref.getIdEngineer())
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

                (bind.rvPortfolio.adapter as ProfilePortfolioAdapter).addList(list)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_portfolio -> {
                intentsResults<PortfolioActivity>(activity, INTENT_ADD)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ProfileEngineerFragment.INTENT_ADD && resultCode == Activity.RESULT_OK) {
            setPortfolio()
        } else if (requestCode == ProfileEngineerFragment.INTENT_EDIT && resultCode == Activity.RESULT_OK) {
            setPortfolio()
        }
    }
}