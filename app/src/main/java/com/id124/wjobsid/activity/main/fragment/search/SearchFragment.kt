package com.id124.wjobsid.activity.main.fragment.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.detail_profile.ProfileDetailActivity
import com.id124.wjobsid.activity.main.fragment.search.adapter.SearchEngineerAdapter
import com.id124.wjobsid.base.BaseFragmentCoroutine
import com.id124.wjobsid.databinding.FragmentSearchBinding
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.model.engineer.EngineerResponse
import com.id124.wjobsid.service.EngineerApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : BaseFragmentCoroutine<FragmentSearchBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_search
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebDevRecyclerView()
        getAllEngineer(null)
    }

    private fun setupWebDevRecyclerView() {
        bind.svEngineer.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getAllEngineer(newText!!)
                return true
            }
        })

        bind.rvEngineer.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        bind.rvEngineer.isNestedScrollingEnabled = false

        val adapter = SearchEngineerAdapter()
        bind.rvEngineer.adapter = adapter

        adapter.setOnItemClickCallback(object: SearchEngineerAdapter.OnItemClickCallback {
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

    private fun getAllEngineer(search: String?) {
        val service = createApi<EngineerApiService>(activity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.getAllEngineer(search = search)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is EngineerResponse) {
                val list = response.data.map {
                    EngineerModel(
                        enId = it.enId,
                        acId = it.acId,
                        acName = it.acName,
                        enJobTitle = it.enJobTitle,
                        enJobType = it.enJobType,
                        enDomicile = it.enDomicile,
                        enDescription = it.enDescription,
                        enProfile = it.enProfile
                    )
                }

                (bind.rvEngineer.adapter as SearchEngineerAdapter).addList(list)
            }
        }
    }
}