package com.id124.wjobsid.activity.main.fragment.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.ProfileDetailActivity
import com.id124.wjobsid.activity.main.fragment.search.adapter.SearchEngineerAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentSearchBinding
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.util.Utils


class SearchFragment : BaseFragmentCoroutine<FragmentSearchBinding>(), SearchContract.View, View.OnClickListener {
    private var presenter: SearchPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_search
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SearchPresenter(createApi(activity))

        bind.ivFilter.setOnClickListener(this@SearchFragment)

        setSearchView()
        setWebDevRecyclerView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_filter -> {
                selectFilter()
            }
        }
    }

    override fun onResultSuccess(list: List<EngineerModel>) {
        (bind.rvEngineer.adapter as SearchEngineerAdapter).addList(list)
        bind.rvEngineer.visibility = View.VISIBLE
        bind.tvDataNotFound.visibility = View.GONE
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
        bind.tvDataNotFound.visibility = View.GONE
        bind.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        bind.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this@SearchFragment)
        presenter?.callServiceSearch(null)
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
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    presenter?.callServiceSearch(null)
                } else {
                    if (newText?.length == 3) {
                        presenter?.callServiceSearch(newText)
                    }
                }

                return true
            }
        })
    }

    private fun setWebDevRecyclerView() {
        val offsetPx = resources.getDimension(R.dimen.bottom_end_recyclerview)
        val bottomOffsetDecoration = Utils.Companion.BottomOffsetDecoration(offsetPx.toInt())
        bind.rvEngineer.addItemDecoration(bottomOffsetDecoration)

        bind.rvEngineer.isNestedScrollingEnabled = false
        bind.rvEngineer.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = SearchEngineerAdapter()
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

    private fun selectFilter() {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Filter")
        builder?.setIcon(R.drawable.ic_filter_gray)

        val user = arrayOf("Name", "Skill", "Domicile", "Freelance", "Full Time")
        builder?.setItems(user) { _, which ->
            when (which) {
                0 -> {
                    presenter?.callServiceFilter(0)
                }
                1 -> {
                    presenter?.callServiceFilter(1)
                }
                2 -> {
                    presenter?.callServiceFilter(2)
                }
                3 -> {
                    presenter?.callServiceFilter(3)
                }
                4 -> {
                    presenter?.callServiceFilter(4)
                }
            }
        }?.show()
    }
}