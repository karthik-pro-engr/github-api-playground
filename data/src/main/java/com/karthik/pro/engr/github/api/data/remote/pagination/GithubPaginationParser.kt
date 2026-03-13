package com.karthik.pro.engr.github.api.data.remote.pagination

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

fun parseNextPageFromLinkHeader(linkHeader: String?): Int? {
    if (linkHeader.isNullOrBlank()) return null
    val parts = linkHeader.split(",")
    for (part in parts) {
        val sections = part.trim().split(";")
        if (sections.size < 2) continue
        val urlPart = sections[0].trim().removePrefix("<").removeSuffix(">")
        val rel = sections[1].trim()
        if (rel == "rel=\"next\"") {
            val pageFromHttpUrl = urlPart.toHttpUrlOrNull()?.queryParameter("page")?.toIntOrNull()
            if (pageFromHttpUrl != null) return pageFromHttpUrl
        }
    }
    return null
}