package com.karthik.pro.engr.github.api.data.remote.dto

data class GitHubOwnerDto(
    val login: String,
    val id: Long,
    val avatar_url: String?,
    val html_url: String
)
