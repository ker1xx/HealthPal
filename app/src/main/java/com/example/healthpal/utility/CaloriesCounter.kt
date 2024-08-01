package com.example.healthpal.utility

import kotlin.time.Duration

object CaloriesCounter {
    fun countCalories(
        duration: Duration,
    ) : Int{
        return Math.round(duration.inWholeSeconds * 0.125).toInt()
    }
}