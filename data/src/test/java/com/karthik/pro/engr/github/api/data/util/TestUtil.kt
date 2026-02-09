package com.karthik.pro.engr.github.api.data.util

import java.io.BufferedReader
import java.io.InputStreamReader

object FakeResponseLoader {
    fun load(path: String): String {
        val stream = this::class.java.classLoader!!.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Test resource not found: $path")
        return BufferedReader(InputStreamReader(stream)).use { it.readText() }
    }
}
