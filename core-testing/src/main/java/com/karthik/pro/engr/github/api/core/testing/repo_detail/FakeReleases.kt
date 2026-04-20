package com.karthik.pro.engr.github.api.core.testing.repo_detail

import com.karthik.pro.engr.github.api.domain.model.Asset
import com.karthik.pro.engr.github.api.domain.model.Release

object FakeReleases {
    fun releases() = listOf(
        Release(
            id = 294359169,
            title = "v1.0.0-TEST-12345",
            version = "v1.0.0-TEST-12345",
            description = "test release for upload",
            date = "2026-03-08T07:01:28Z",
            authorName = "karthik-pro-engr",
            authorAvatar = "https://avatars.githubusercontent.com/u/101930095?v=4",
            isStable = true,
            assets = emptyList()
        ),
        Release(
            id = 294354682,
            title = "v1.0.0-TEST-1234",
            version = "v1.0.0-TEST-1234",
            description = "test release for upload",
            date = "2026-03-08T06:10:55Z",
            authorName = "karthik-pro-engr",
            authorAvatar = "https://avatars.githubusercontent.com/u/101930095?v=4",
            isStable = true,
            assets = emptyList()
        ),
        Release(
            id = 294166063,
            title = "v1.0.0-TEST-123",
            version = "v1.0.0-TEST-123",
            description = "test release for upload",
            date = "2026-03-07T02:15:07Z",
            authorName = "karthik-pro-engr",
            authorAvatar = "https://avatars.githubusercontent.com/u/101930095?v=4",
            isStable = true,
            assets = listOf(
                Asset(
                    name = "my_video.mov",
                    size = 265405,
                    downloadUrl = "https://github.com/karthik-pro-engr/github-api-playground/releases/download/v1.0.0-TEST-123/my_video.mov"
                )
            )
        )
    )

    fun releaseItem() = Release(
        id = 294166063,
        title = "v1.0.0-TEST-123",
        version = "v1.0.0-TEST-123",
        description = "test release for upload",
        date = "2026-03-07T02:15:07Z",
        authorName = "karthik-pro-engr",
        authorAvatar = "https://avatars.githubusercontent.com/u/101930095?v=4",
        isStable = true,
        assets = listOf(
            Asset(
                name = "my_video.mov",
                size = 265405,
                downloadUrl = "https://github.com/karthik-pro-engr/github-api-playground/releases/download/v1.0.0-TEST-123/my_video.mov"
            )
        )
    )
}