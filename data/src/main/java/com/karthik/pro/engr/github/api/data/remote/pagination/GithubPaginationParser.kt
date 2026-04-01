package com.karthik.pro.engr.github.api.data.remote.pagination

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

object GithubPaginationParser {
    private const val REL_PREV = "rel=\"prev\""
    private const val REL_NEXT = "rel=\"next\""
    private const val PARAM_PAGE = "page"

    data class PageLinks(
        val previousKey: Int?,
        val nextKey: Int?
    )

    fun parsePageNumbers(linkHeader: String?): PageLinks {
        if (linkHeader.isNullOrBlank()) return PageLinks(null, null)
        val parts = linkHeader.split(",")

        var previousPage: Int? = null
        var nextPage: Int? = null

        for (part in parts) {
            val sections = part.trim().split(";")
            if (sections.size < 2) continue

            val urlPart = sections[0].trim().removePrefix("<").removeSuffix(">")
            val rel = sections[1].trim()

            val pageNumber = urlPart.toHttpUrlOrNull()?.queryParameter(PARAM_PAGE)?.toIntOrNull()

            when (rel) {
                REL_PREV -> previousPage = pageNumber
                REL_NEXT -> nextPage = pageNumber
            }
        }
        return PageLinks(previousKey = previousPage, nextKey = nextPage)
    }

}