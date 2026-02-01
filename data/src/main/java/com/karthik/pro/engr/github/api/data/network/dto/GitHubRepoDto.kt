package com.karthik.pro.engr.github.api.data.network.dto

data class GitHubRepoDto(
    val id: Long,
    val name: String,
    val full_name: String,
    val description: String?,
    val html_url: String,
    val language: String?,
    val stargazers_count: Int,
    val forks_count: Int,
    val owner: GitHubOwnerDto
)
