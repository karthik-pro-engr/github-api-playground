package com.karthik.pro.engr.github.api.playground.presentation.repo.components.releases.model

import com.karthik.pro.engr.github.api.domain.model.Asset

data class ReleaseUi(
    val id: Long,
    val version: String,
    val date: String,
    val isLatest: Boolean,
    val description: String,
    val authorName: String,
    val authorAvatar: String,
    val isStable: Boolean,
    val assets: List<Asset>
)