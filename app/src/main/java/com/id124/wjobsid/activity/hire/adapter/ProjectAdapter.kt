package com.id124.wjobsid.activity.hire.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemProjectSpinnerBinding
import com.id124.wjobsid.model.project.ProjectModel

class ProjectAdapter(private var context: Context) : BaseAdapter() {
    private lateinit var bind: ItemProjectSpinnerBinding
    private var items = mutableListOf<ProjectModel>()

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup): View {
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            bind = DataBindingUtil.inflate(inflater, R.layout.item_project_spinner, parent, false)
        }

        bind.project = items[i]

        return bind.root
    }

    fun addList(list: List<ProjectModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}