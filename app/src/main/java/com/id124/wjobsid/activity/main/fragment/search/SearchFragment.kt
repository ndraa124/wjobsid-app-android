package com.id124.wjobsid.activity.main.fragment.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.ProfileDetailActivity
import com.id124.wjobsid.activity.main.fragment.search.adapter.SearchEngineerAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentSearchBinding
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.util.Utils

class SearchFragment : BaseFragmentCoroutine<FragmentSearchBinding>(), SearchContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private var presenter: SearchPresenter? = null

    private lateinit var adapter: SearchEngineerAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var page = 1
    private var totalPage = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_search
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SearchPresenter(createApi(activity))
        bind.ivFilter.setOnClickListener(this@SearchFragment)
        bind.swipeRefresh.setOnRefreshListener(this@SearchFragment)

        setSearchView()
        setWebDevRecyclerView()
        setupDataEngineer()
        setupAddOnScroll()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_filter -> {
                selectFilter()
            }
        }
    }

    override fun onRefresh() {
        bind.shimmerViewContainer.startShimmerAnimation()
        bind.shimmerViewContainer.visibility = View.VISIBLE

        adapter.clear()
        page = 1
        presenter?.callServiceSearch(
            search = null,
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
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            bind.rvEngineer.visibility = View.GONE
            bind.tvDataNotFound.visibility = View.VISIBLE
            bind.dataNotFound = message
        }
    }

    override fun showLoading() {
        isLoading = true
        bind.swipeRefresh.isRefreshing = true
        bind.tvDataNotFound.visibility = View.GONE
    }

    override fun hideLoading() {
        isLoading = false

        bind.swipeRefresh.isRefreshing = false
        bind.shimmerViewContainer.stopShimmerAnimation()
        bind.shimmerViewContainer.visibility = View.GONE
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

    private fun setSearchView() {
        bind.svEngineer.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.clear()
                page = 1

                if (query == "") {
                    presenter?.callServiceSearch(
                        search = null,
                        page = page
                    )
                } else {
                    presenter?.callServiceSearch(
                        search = query,
                        page = page
                    )
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.clear()
                page = 1

                if (newText == "") {
                    presenter?.callServiceSearch(
                        search = null,
                        page = page
                    )
                } else {
                    if (newText?.length!! == 3) {
                        presenter?.callServiceSearch(
                            search = newText,
                            page = page
                        )
                    }
                }

                return true
            }
        })
    }

    private fun setWebDevRecyclerView() {
        val offsetPx = resources.getDimension(R.dimen.bottom_end_recyclerview_home)
        val bottomOffsetDecoration = Utils.Companion.BottomOffsetDecoration(offsetPx.toInt())

        bind.rvEngineer.addItemDecoration(bottomOffsetDecoration)
        bind.rvEngineer.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        bind.rvEngineer.layoutManager = layoutManager

        adapter = SearchEngineerAdapter()
        bind.rvEngineer.adapter = adapter

        adapter.setOnItemClickCallback(object : SearchEngineerAdapter.OnItemClickCallback {
            override fun onItemClick(data: EngineerModel) {
                val intent = Intent(activity, ProfileDetailActivity::class.java)
                intent.putExtra("en_id", data.enId)
                intent.putExtra("ac_id", data.acId)
                intent.putExtra("ac_name", data.acName)
                intent.putExtra("en_job_title", data.enJobTitle)
                intent.putExtra("en_domicile", data.enDomicile)
                intent.putExtra("en_job_type", data.enJobType)
                intent.putExtra("en_description", data.enDescription)
                startActivity(intent)
            }
        })
    }

    private fun setupDataEngineer() {
        bind.shimmerViewContainer.startShimmerAnimation()
        bind.shimmerViewContainer.visibility = View.VISIBLE

        presenter?.bindToView(this@SearchFragment)

        adapter.clear()
        presenter?.callServiceSearch(
            search = null,
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
                        presenter?.callServiceSearch(
                            search = null,
                            page = page
                        )
                    }
                }

                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun selectFilter() {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Filter")
        builder?.setIcon(R.drawable.ic_filter_gray)

        val user = arrayOf("Name", "Domicile", "Freelance", "Full Time")
        builder?.setItems(user) { _, which ->
            when (which) {
                0 -> {
                    adapter.clear()
                    presenter?.callServiceFilter(
                        filter = 0
                    )
                }
                1 -> {
                    adapter.clear()
                    presenter?.callServiceFilter(
                        filter = 1
                    )
                }
                2 -> {
                    adapter.clear()
                    presenter?.callServiceFilter(
                        filter = 2
                    )
                }
                3 -> {
                    adapter.clear()
                    presenter?.callServiceFilter(
                        filter = 3
                    )
                }
            }
        }?.show()
    }
}