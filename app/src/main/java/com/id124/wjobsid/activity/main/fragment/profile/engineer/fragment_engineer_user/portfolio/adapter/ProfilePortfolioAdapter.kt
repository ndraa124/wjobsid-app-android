package com.id124.wjobsid.activity.main.fragment.profile.engineer.fragment_engineer_user.portfolio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemPortfolioBinding
import com.id124.wjobsid.model.portfolio.PortfolioModel
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE_DEFAULT_BACKGROUND

class ProfilePortfolioAdapter : RecyclerView.Adapter<ProfilePortfolioAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemPortfolioBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<PortfolioModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(portfolio: PortfolioModel) {
            bind.portfolio = portfolio

            if (portfolio.pr_type == "aplikasi mobile") {
                bind.projectType = "Mobile"
            } else {
                bind.projectType = "Web"
            }

            if (portfolio.pr_image != null) {
                bind.imageUrl = BASE_URL_IMAGE + portfolio.pr_image
            } else {
                bind.imageUrl = BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }

            bind.executePendingBindings()

            bind.cvProject.setOnClickListener {
                onItemClickCallback.onItemClick(portfolio)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_portfolio, parent, false)
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
        fun onItemClick(data: PortfolioModel)
    }

    fun addList(list: List<PortfolioModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}