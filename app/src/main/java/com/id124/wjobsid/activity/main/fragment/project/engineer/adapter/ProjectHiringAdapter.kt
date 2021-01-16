package com.id124.wjobsid.activity.main.fragment.project.engineer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemHireBinding
import com.id124.wjobsid.model.hire.HireModel
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE_DEFAULT_BACKGROUND
import com.id124.wjobsid.util.Utils

class ProjectHiringAdapter : RecyclerView.Adapter<ProjectHiringAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemHireBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<HireModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hire: HireModel) {
            bind.hire = hire

            if (hire.pjImage != null) {
                bind.imageUrl = BASE_URL_IMAGE + hire.pjImage
            } else {
                bind.imageUrl = BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }

            if (hire.hrDateConfirm != null) {
                when (hire.hrStatus) {
                    "approve" -> {
                        bind.tvConfirmDate.setTextColor(itemView.context.resources.getColor(R.color.nephritis, itemView.context.theme))
                        bind.tvConfirmDate.visibility = View.VISIBLE
                        bind.date = "- approve at ${hire.hrDateConfirm}"
                    }
                    "reject" -> {
                        bind.tvConfirmDate.setTextColor(itemView.context.resources.getColor(R.color.red, itemView.context.theme))
                        bind.tvConfirmDate.visibility = View.VISIBLE
                        bind.date = "- reject at ${hire.hrDateConfirm}"
                    }
                    else -> {
                        bind.tvConfirmDate.setTextColor(itemView.context.resources.getColor(R.color.yellow, itemView.context.theme))
                        bind.tvConfirmDate.visibility = View.GONE
                        bind.date = "- wait"
                    }
                }
            } else {
                bind.tvConfirmDate.visibility = View.GONE
            }

            bind.price = "Rp. ${Utils.currencyFormat(hire.hrPrice.toString())}"

            bind.executePendingBindings()

            bind.cvProject.setOnClickListener {
                onItemClickCallback.onItemClick(hire)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_hire,
            parent,
            false
        )
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
        fun onItemClick(data: HireModel)
    }

    fun addList(list: List<HireModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}