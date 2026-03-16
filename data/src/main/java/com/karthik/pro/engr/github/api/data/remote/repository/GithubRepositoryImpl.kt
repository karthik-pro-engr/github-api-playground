package com.karthik.pro.engr.github.api.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.karthik.pro.engr.github.api.data.remote.api.GithubService
import com.karthik.pro.engr.github.api.data.remote.error.ErrorParser
import com.karthik.pro.engr.github.api.data.remote.pagination.GithubPagingSource
import com.karthik.pro.engr.github.api.domain.constants.PaginationConstants.DEFAULT_PAGE_SIZE
import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val service: GithubService,
    private val errorParser: ErrorParser
) : GithubRepository {
    override fun getUserRepos(
        username: String,
    ): Flow<PagingData<Repo>> {
        val pageSize = DEFAULT_PAGE_SIZE
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            pagingSourceFactory = {
                GithubPagingSource(
                    service = service,
                    errorParser = errorParser,
                    username = username,
                    perPage = pageSize
                )
            }
        ).flow
    }
}