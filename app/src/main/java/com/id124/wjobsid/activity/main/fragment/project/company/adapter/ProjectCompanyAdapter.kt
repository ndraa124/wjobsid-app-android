package com.id124.wjobsid.activity.main.fragment.project.company.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemProjectBinding
import com.id124.wjobsid.model.project.ProjectModel
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE_DEFAULT_BACKGROUND

class ProjectCompanyAdapter : RecyclerView.Adapter<ProjectCompanyAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemProjectBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<ProjectModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(project: ProjectModel) {
            bind.project = project

            if (project.pjImage != null) {
                bind.imageUrl = BASE_URL_IMAGE + project.pjImage
            } else {
                bind.imageUrl = BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }

            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(project)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_project, parent, false)
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: ProjectModel)
    }

    fun addList(list: List<ProjectModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}