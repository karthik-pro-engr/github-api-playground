package com.karthik.pro.engr.github.api.playground.presentation.repo.components.releases.model

import com.karthik.pro.engr.github.api.domain.model.Asset
import com.karthik.pro.engr.github.api.domain.time.RelativeTime

data class ReleaseUi(
    val id: Long,
    val version: String,
    val date: RelativeTime,
    val isLatest: Boolean,
    val description: String,
    val authorName: String,
    val authorAvatar: String,
    val isStable: Boolean,
    val assets: List<Asset>
)