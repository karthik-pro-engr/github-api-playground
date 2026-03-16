package com.karthik.pro.engr.github.api.domain.usecase

import androidx.paging.PagingData
import com.karthik.pro.engr.github.api.domain.error.DomainError
import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.repository.GithubRepository
import com.karthik.pro.engr.github.api.domain.result.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    operator fun invoke(
        username: String
    ): Flow<PagingData<Repo>> =
        repository.getUserRepos(username)
}