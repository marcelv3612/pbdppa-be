// src/main/kotlin/com/bdcga/pbdpp/dto/BigFiveScoresRequest.kt

package com.bdcga.pbdpp.dto

import jakarta.validation.constraints.NotNull

data class BigFiveScoresRequest(
    @field:NotNull(message = "Extraversion score is required")
    val extraversion: Double,

    @field:NotNull(message = "Agreeableness score is required")
    val agreeableness: Double,

    @field:NotNull(message = "Conscientiousness score is required")
    val conscientiousness: Double,

    @field:NotNull(message = "Neuroticism score is required")
    val neuroticism: Double,

    @field:NotNull(message = "Openness score is required")
    val openness: Double
)