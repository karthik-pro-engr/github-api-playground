package com.karthik.pro.engr.github.api.data.repository

import com.karthik.pro.engr.github.api.core.di.IoDispatcher
import com.karthik.pro.engr.github.api.data.mapper.RepoMapper
import com.karthik.pro.engr.github.api.data.mapper.toDomainError
import com.karthik.pro.engr.github.api.data.remote.GithubService
import com.karthik.pro.engr.github.api.data.remote.util.safeApiCall
import com.karthik.pro.engr.github.api.domain.error.DomainError
import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.repository.GithubRepository
import com.karthik.pro.engr.github.api.domain.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val service: GithubService,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    GithubRepository {
    override fun getUserRepos(
        username: String,
        perPage: Int,
        page: Int
    ): Flow<Result<List<Repo>, DomainError>> = flow {
        when (val result = safeApiCall { service.listUserRepos(username, perPage, page) }) {
            is Result.Failure -> emit(Result.Failure(result.error.toDomainError()))
            is Result.Success -> emit(Result.Success(RepoMapper.fromDtoList(result.data)))
        }
    }.flowOn(ioDispatcher)
}