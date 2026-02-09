package com.karthik.pro.engr.github.api.data.remote

import com.karthik.pro.engr.github.api.data.remote.dto.GitHubRepoDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users/{username}/repos")
    suspend fun listUserRepos(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ): List<GitHubRepoDto>
}