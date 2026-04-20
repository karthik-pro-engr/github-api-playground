package com.karthik.pro.engr.github.api.domain.model

data class Release(
    val id: Long,
    val title: String,
    val version: String,
    val description: String,
    val date: String,
    val authorName: String,
    val authorAvatar: String,
    val isStable: Boolean,
    val assets: List<Asset>
)
