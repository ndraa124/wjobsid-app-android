package com.id124.wjobsid.activity.main.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemEngineerBinding
import com.id124.wjobsid.model.engineer.EngineerModel
import com.id124.wjobsid.remote.ApiClient.Companion.BASE_URL_IMAGE

class HomeEngineerAdapter : RecyclerView.Adapter<HomeEngineerAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemEngineerBinding
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var items = mutableListOf<EngineerModel>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(en: EngineerModel) {
            bind.engineer = en
            bind.imageUrl = BASE_URL_IMAGE + en.enProfile
            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(en)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_engineer, parent, false)
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: EngineerModel)
    }

    fun addList(list: List<EngineerModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}