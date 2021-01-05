package com.id124.wjobsid.activity.main.fragment.project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemHireBinding
import com.id124.wjobsid.databinding.ItemProjectBinding
import com.id124.wjobsid.model.hire.HireModel
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE

class ProjectHiringAdapter : RecyclerView.Adapter<ProjectHiringAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemHireBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<HireModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(hire: HireModel) {
            bind.hire = hire
            bind.imageUrl = BASE_URL_IMAGE + hire.cnProfile
            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(hire)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_hire, parent, false)
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: HireModel)
    }

    fun addList(list: List<HireModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}