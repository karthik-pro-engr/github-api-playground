package com.karthik.pro.engr.github.api.data.testutil

import java.io.BufferedReader
import java.io.InputStreamReader

object JsonReader {
    fun read(path: String): String {
        val stream = checkNotNull(
            this::class.java.classLoader?.getResourceAsStream(path)
        ) { "Resource not found: $path" }

        return BufferedReader(InputStreamReader(stream)).use { it.readText() }
    }
}