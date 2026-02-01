package com.karthik.pro.engr.github.api.data.mapper

import com.karthik.pro.engr.github.api.data.network.dto.GitHubRepoDto
import com.karthik.pro.engr.github.api.domain.model.Owner
import com.karthik.pro.engr.github.api.domain.model.Repo

object RepoMapper {
    fun fromDto(dto: GitHubRepoDto) = Repo(
        id = dto.id,
        name = dto.name,
        fullName = dto.full_name,
        description = dto.description,
        htmlUrl = dto.html_url,
        language = dto.language,
        stars = dto.stargazers_count,
        forks = dto.forks_count,
        owner = Owner(
            login = dto.owner.login,
            id = dto.owner.id,
            avatarUrl = dto.owner.avatar_url,
            htmlUrl = dto.owner.html_url
        )
    )

    fun fromDtoList(dtos: List<GitHubRepoDto>): List<Repo> = dtos.map { fromDto(it) }
}