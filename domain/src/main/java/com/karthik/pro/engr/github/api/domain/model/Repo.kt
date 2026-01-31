package com.karthik.pro.engr.github.api.domain.model

data class Repo(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val htmlUrl: String,
    val language: String?,
    val stars: Int,
    val forks: Int,
    val owner: Owner
)
