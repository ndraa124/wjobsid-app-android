package com.id124.wjobsid.activity.main.fragment.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemSkillBinding
import com.id124.wjobsid.model.skill.SkillModel

class SearchSkillAdapter : RecyclerView.Adapter<SearchSkillAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemSkillBinding
    private var items = mutableListOf<SkillModel>()

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(skill: SkillModel) {
            bind.skill = skill
            bind.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_skill,
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
        return if(items.size > 3){
            3
        } else {
            items.size
        }
    }

    fun addList(list: List<SkillModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}