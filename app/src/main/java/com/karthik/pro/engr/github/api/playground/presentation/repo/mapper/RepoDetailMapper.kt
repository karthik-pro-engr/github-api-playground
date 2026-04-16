package com.karthik.pro.engr.github.api.playground.presentation.repo.mapper

import com.karthik.pro.engr.github.api.domain.model.Repo
import com.karthik.pro.engr.github.api.playground.presentation.repo.model.RepoDetailUi
import kotlin.math.floor


fun Int.toReadable(): String {

    if (this < 1000)
        return this.toString()

    val value = this / 1000.0

    val floored = floor(value * 10) / 10

    return if (floored % 1.0 == 0.0) {
        "${floored.toInt()}k"
    } else {
        "${floored}k"
    }

}

fun Repo.repoDetailUi() {
    RepoDetailUi(
        id = id,
        name = name,
        fullName = fullName,
        description = description.orEmpty(),
        htmlUrl = htmlUrl,
        language = language.orEmpty(),
        languagesUrl = languagesUrl,
        stars = stars.toReadable(),
        forks = forks.toReadable(),
        owner = owner,
        releases = emptyList()
    )
}
