package com.id124.wjobsid.activity.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemEngineerBinding
import com.id124.wjobsid.model.EngineerModel

class HomeEngineerAdapter(private val model: List<EngineerModel>) : RecyclerView.Adapter<HomeEngineerAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemEngineerBinding
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    
    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(engineer: EngineerModel) {
            bind.engineer = engineer
            bind.imageUrl = engineer.en_profile
            bind.executePendingBindings()

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(engineer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_engineer, parent, false)
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }

    interface OnItemClickCallback {
        fun onItemClick(data: EngineerModel)
    }
}