package com.karthik.pro.engr.github.api.domain.usecase

import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    operator fun invoke(username: String, perPage: Int = 30, page: Int = 1): Flow<List<Repo>> =
        repository.getUserRepos(username, perPage, page)
}