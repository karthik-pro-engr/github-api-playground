package com.karthik.pro.engr.github.api.data.remote.dto.request

data class CreateReleaseBody(
    val body: String,
    val draft: Boolean,
    val name: String,
    val prerelease: Boolean,
    val tag_name: String
)