package com.id124.wjobsid.model.engineer

import com.id124.wjobsid.model.skill.SkillModel

data class EngineerModel(
    val enId: Int,
    val acId: Int,
    val acName: String,
    val enJobTitle: String? = null,
    val enJobType: String? = null,
    val enDomicile: String? = null,
    val enDescription: String? = null,
    val enProfile: String? = null,
    val enSkill: List<SkillModel>? = null
)