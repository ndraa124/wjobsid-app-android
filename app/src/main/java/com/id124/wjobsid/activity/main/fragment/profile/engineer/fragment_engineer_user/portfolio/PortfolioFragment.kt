package com.id124.wjobsid.activity.main.fragment.profile.engineer.fragment_engineer_user.portfolio

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.main.fragment.profile.engineer.fragment_engineer_user.portfolio.adapter.ProfilePortfolioAdapter
import com.id124.wjobsid.activity.portfolio.PortfolioActivity
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentPortfolioBinding
import com.id124.wjobsid.model.portfolio.PortfolioModel
import kotlinx.android.synthetic.main.fragment_portfolio.view.*

class PortfolioFragment : BaseFragmentCoroutine<FragmentPortfolioBinding>(), PortfolioContract.View, View.OnClickListener {
    private var presenter: PortfolioPresenter? = null

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
        presenter = PortfolioPresenter(createApi(activity))

        if (sharedPref.getInDetail() == 0) {
            bind.btnAddPortfolio.visibility = View.VISIBLE
        } else {
            bind.btnAddPortfolio.visibility = View.GONE
        }

        bind.btnAddPortfolio.setOnClickListener(this@PortfolioFragment)
        setupPortfolioRecyclerView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_portfolio -> {
                startActivityForResult(Intent(activity, PortfolioActivity::class.java), INTENT_ADD)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INTENT_ADD && resultCode == Activity.RESULT_OK) {
            presenter?.callService(sharedPref.getIdEngineer())
        } else if (requestCode == INTENT_EDIT && resultCode == Activity.RESULT_OK) {
            presenter?.callService(sharedPref.getIdEngineer())
        }
    }

    override fun onResultSuccess(list: List<PortfolioModel>) {
        (bind.rvPortfolio.adapter as ProfilePortfolioAdapter).addList(list)
        bind.rvPortfolio.visibility = View.VISIBLE
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            bind.rvPortfolio.visibility = View.GONE
            bind.tvDataNotFound.visibility = View.VISIBLE
            bind.dataNotFound = message
        }
    }

    override fun showLoading() {
        bind.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        bind.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this@PortfolioFragment)
        presenter?.callService(sharedPref.getIdEngineer())
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
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
}