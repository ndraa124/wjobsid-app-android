package com.id124.wjobsid.activity.main.fragment.profile.engineer_user.fragment_engineer_user.portfolio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemPortfolioBinding
import com.id124.wjobsid.model.portfolio.PortfolioModel

class ProfilePortfolioAdapter(private val model: List<PortfolioModel>) : RecyclerView.Adapter<ProfilePortfolioAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemPortfolioBinding

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(portfolio: PortfolioModel) {
            bind.imageUrl = portfolio.pr_image
            bind.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_portfolio, parent, false)
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }
}