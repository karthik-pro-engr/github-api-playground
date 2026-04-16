package com.karthik.pro.engr.github.api.playground.presentation.repo.model

import com.karthik.pro.engr.github.api.domain.model.Owner
import com.karthik.pro.engr.github.api.playground.presentation.repo.components.releases.model.ReleaseUi

data class RepoDetailUi(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val htmlUrl: String,
    val language: String?,
    val languagesUrl:String,
    val stars: String,
    val forks: String,
    val releases: List<ReleaseUi>,
    val owner: Owner
)
