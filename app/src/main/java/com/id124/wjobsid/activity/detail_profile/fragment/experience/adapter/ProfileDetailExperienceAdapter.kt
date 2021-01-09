package com.id124.wjobsid.activity.detail_profile.fragment.experience.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemExperienceBinding
import com.id124.wjobsid.model.experience.ExperienceModel
import com.id124.wjobsid.util.Utils.Companion.rangeMonth

class ProfileDetailExperienceAdapter : RecyclerView.Adapter<ProfileDetailExperienceAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemExperienceBinding
    private var items = mutableListOf<ExperienceModel>()

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(experience: ExperienceModel) {
            bind.experience = experience
            bind.rangeDate = rangeMonth(
                experience.ex_start!!.split('T')[0],
                experience.ex_end!!.split('T')[0]
            ) + " month"
            bind.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_experience, parent, false)
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addList(list: List<ExperienceModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}