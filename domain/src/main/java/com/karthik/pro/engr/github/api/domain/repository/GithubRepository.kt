package com.karthik.pro.engr.github.api.domain.repository

import androidx.paging.PagingData
import com.karthik.pro.engr.github.api.domain.model.Repo
import kotlinx.coroutines.flow.Flow


interface GithubRepository {
    fun getUserRepos(
        username: String
    ): Flow<PagingData<Repo>>
}