package com.id124.wjobsid.activity.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.id124.wjobsid.R
import com.id124.wjobsid.databinding.ItemExperienceBinding
import com.id124.wjobsid.model.ExperienceModel

class ProfileExperienceAdapter(private val model: List<ExperienceModel>) : RecyclerView.Adapter<ProfileExperienceAdapter.RecyclerViewHolder>() {
    private lateinit var bind: ItemExperienceBinding

    inner class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(experience: ExperienceModel) {
            bind.experience = experience
            bind.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        bind = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_experience, parent, false)
        return RecyclerViewHolder(bind.root)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(model[position])
    }

    override fun getItemCount(): Int {
        return model.size
    }
}