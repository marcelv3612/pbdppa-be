package com.bdcga.pbdpp.dto// package com.bdcga.pbdpp.dto

import com.bdcga.pbdpp.model.BigFiveScores
import com.bdcga.pbdpp.model.TimeSlot
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank
    val username: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:Size(min = 6)
    val password: String,

    val bigFiveScores: BigFiveScores? = null,

    val availability: List<TimeSlot> = emptyList()
)

data class AuthResponse(
    val accessToken: String,
    val tokenType: String = "Bearer"
)

data class LoginRequest(
    val usernameOrEmail: String,
    val password: String
)