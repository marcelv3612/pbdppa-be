package com.bdcga.pbdpp.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Document(collection = "users")
data class User(
    @Id
    val id: String? = null,

    @field:NotBlank
    val username: String,

    @field:Email
    @field:NotBlank
    val email: String,

    val passwordHash: String,

    val bigFiveScores: BigFiveScores? = null,

    val availability: List<TimeSlot> = emptyList()
)