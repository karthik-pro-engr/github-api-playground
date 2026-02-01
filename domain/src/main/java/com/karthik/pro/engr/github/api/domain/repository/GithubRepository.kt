package com.karthik.pro.engr.github.api.domain.repository

import com.karthik.pro.engr.github.api.domain.model.Repo
import kotlinx.coroutines.flow.Flow


interface GithubRepository {
    fun getUserRepos(username: String, perPage: Int = 30, page: Int = 1): Flow<List<Repo>>
}