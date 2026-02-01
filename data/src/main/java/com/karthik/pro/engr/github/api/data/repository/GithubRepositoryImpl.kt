package com.karthik.pro.engr.github.api.data.repository

import com.karthik.pro.engr.github.api.data.mapper.RepoMapper
import com.karthik.pro.engr.github.api.data.network.GithubService
import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.repository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val service: GithubService) :
    GithubRepository {
    override fun getUserRepos(
        username: String,
        perPage: Int,
        page: Int
    ): Flow<List<Repo>> = flow {
        val dtos = service.listUserRepos(username, perPage, page)
        emit(RepoMapper.fromDtoList(dtos))
    }.flowOn(Dispatchers.IO)
}