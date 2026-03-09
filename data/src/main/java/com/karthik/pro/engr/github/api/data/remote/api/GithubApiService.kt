package com.karthik.pro.engr.github.api.data.remote.api

import com.karthik.pro.engr.github.api.data.remote.dto.request.CreateReleaseBody
import com.karthik.pro.engr.github.api.data.remote.dto.response.ReleaseResponse
import com.karthik.pro.engr.github.api.data.remote.dto.response.GitHubRepoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("users/{username}/repos")
    suspend fun listUserRepos(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ): List<GitHubRepoDto>

    @POST("repos/{owner}/{repo_name}/releases")
    suspend fun createReleases(
        @Path("owner") owner: String,
        @Path("repo_name") repoName: String,
        @Body createReleaseBody: CreateReleaseBody
    ): Response<ReleaseResponse>

    @GET("repos/{owner}/{repo_name}/releases/tags/{tag}")
    suspend fun getReleaseByTag(
        @Path("owner") owner: String,
        @Path("repo_name") repoName: String,
        @Path("tag") tag: String
    ):Response<ReleaseResponse>

}