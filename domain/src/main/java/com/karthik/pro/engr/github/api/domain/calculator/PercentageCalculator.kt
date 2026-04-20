package com.karthik.pro.engr.github.api.domain.calculator

object PercentageCalculator {

    fun calculate(bytes: Int, total: Int): Float {
        if (total == 0) return 0f
        return (bytes.toFloat() / total) * 100f
    }
}