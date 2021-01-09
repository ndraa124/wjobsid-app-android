package com.id124.wjobsid.activity.detail_profile.fragment.portfolio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemPortfolioBinding
import com.id124.wjobsid.model.portfolio.PortfolioModel
import com.id124.wjobsid.remote.ApiClient
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE

class ProfileDetailPortfolioAdapter : RecyclerView.Adapter<ProfileDetailPortfolioAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemPortfolioBinding
    private var items = mutableListOf<PortfolioModel>()

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(portfolio: PortfolioModel) {
            if (portfolio.pr_image != null) {
                bind.imageUrl = BASE_URL_IMAGE + portfolio.pr_image
            } else {
                bind.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }

            bind.executePendingBindings()
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

    fun addList(list: List<PortfolioModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}