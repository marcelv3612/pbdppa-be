package com.bdcga.pbdpp.dto// package com.bdcga.pbdpp.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:Email(message = "Invalid email address")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, message = "Password must be at least 8 characters")
    val password: String,

    @field:NotBlank(message = "Gender is required")
    val gender: String,

    @field:Min(value = 0, message = "Software experience cannot be negative")
    val softwareExperience: Int
)

data class AuthResponse(
    val token: String? = null,
    val error: String? = null
)

data class LoginRequest(
    val usernameOrEmail: String,
    val password: String
)