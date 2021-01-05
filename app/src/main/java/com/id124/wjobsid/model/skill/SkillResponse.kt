package com.id124.wjobsid.model.skill

import com.google.gson.annotations.SerializedName

data class SkillResponse(val success: Boolean, val message: String, val data: List<SkillItem>) {
    data class SkillItem(
        @SerializedName("sk_id")
        val sk_id: Int,

        @SerializedName("sk_skill_name")
        val skSkillName: String
    )
}