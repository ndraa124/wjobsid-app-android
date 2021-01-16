package com.id124.wjobsid.activity.main.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.ProfileDetailActivity
import com.id124.wjobsid.activity.github.GithubActivity
import com.id124.wjobsid.activity.main.fragment.home.adapter.HomeEngineerAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentHomeBinding
import com.id124.wjobsid.model.account.AccountModel
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.util.SharedPreference.Companion.AC_NAME
import com.id124.wjobsid.util.Utils

class HomeFragment : BaseFragmentCoroutine<FragmentHomeBinding>(), HomeContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private var presenter: HomePresenter? = null

    private lateinit var adapter: HomeEngineerAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var page = 1
    private var totalPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_home
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomePresenter(createApi(activity))
        bind.ivGithub.setOnClickListener(this@HomeFragment)
        bind.swipeRefresh.setOnRefreshListener(this@HomeFragment)

        if (sharedPref.getLevelUser() == 0) {
            bind.title = "Login as Engineer"
        } else {
            bind.title = "Login as Company"
        }

        bind.accountModel = AccountModel(acName = "Hai, ${userDetail[AC_NAME]}")
        setupWebDevRecyclerView()
        setupDataEngineer()
        setupAddOnScroll()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_github -> {
                intents<GithubActivity>(activity)
            }
        }
    }

    override fun onRefresh() {
        bind.shimmerViewContainer.startShimmerAnimation()
        bind.shimmerViewContainer.visibility = View.VISIBLE

        adapter.clear()
        page = 1
        presenter?.callService(
            page = page
        )
    }

    override fun onResultSuccess(list: List<EngineerModel>, totalPages: Int) {
        totalPage = totalPages

        adapter.addList(list)
        bind.rvEngineer.visibility = View.VISIBLE
        bind.tvDataNotFound.visibility = View.GONE

        bind.swipeRefresh.isRefreshing = false
        isLoading = false
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please login!")
            sharedPref.accountLogout()
        } else {
            bind.rvEngineer.visibility = View.GONE
            bind.tvDataNotFound.visibility = View.VISIBLE
            bind.dataNotFound = message
        }
    }

    override fun showLoading() {
        isLoading = true
        bind.tvDataNotFound.visibility = View.GONE
    }

    override fun hideLoading() {
        isLoading = false

        bind.swipeRefresh.isRefreshing = false
        bind.shimmerViewContainer.visibility = View.GONE
        bind.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        bind.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setupWebDevRecyclerView() {
        val offsetPx = resources.getDimension(R.dimen.bottom_end_recyclerview_home)
        val bottomOffsetDecoration = Utils.Companion.BottomOffsetDecoration(offsetPx.toInt())

        bind.rvEngineer.addItemDecoration(bottomOffsetDecoration)
        bind.rvEngineer.isNestedScrollingEnabled = true

        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        bind.rvEngineer.layoutManager = layoutManager

        adapter = HomeEngineerAdapter()
        bind.rvEngineer.adapter = adapter

        adapter.setOnItemClickCallback(object : HomeEngineerAdapter.OnItemClickCallback {
            override fun onItemClick(data: EngineerModel) {
                val intent = Intent(activity, ProfileDetailActivity::class.java)
                intent.putExtra("en_id", data.enId)
                intent.putExtra("ac_id", data.acId)
                intent.putExtra("ac_name", data.acName)
                intent.putExtra("en_job_title", data.enJobTitle)
                intent.putExtra("en_domicile", data.enDomicile)
                intent.putExtra("en_job_type", data.enJobType)
                intent.putExtra("en_description", data.enDescription)
                intent.putExtra("en_profile", data.enProfile)
                startActivity(intent)
            }
        })
    }

    private fun setupDataEngineer() {
        bind.shimmerViewContainer.startShimmerAnimation()
        bind.shimmerViewContainer.visibility = View.VISIBLE

        presenter?.bindToView(this@HomeFragment)

        adapter.clear()
        presenter?.callService(
            page = page
        )
    }

    private fun setupAddOnScroll() {
        bind.rvEngineer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount

                if (!isLoading && page < totalPage) {
                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        page++
                        presenter?.callService(
                            page = page
                        )
                    }
                }

                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
}