package com.karthik.pro.engr.github.api.data.network.dto

data class GitHubOwnerDto(
    val login: String,
    val id: Long,
    val avatar_url: String?,
    val html_url: String
)
