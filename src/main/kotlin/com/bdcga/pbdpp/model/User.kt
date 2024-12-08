// src/main/kotlin/com/bdcga/pbdpp/model/User.kt

package com.bdcga.pbdpp.model

import com.bdcga.pbdpp.dto.TimeSlot
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Id
    val id: String? = null,
    val username: String,
    val email: String,
    val passwordHash: String,
    val gender: String,
    val softwareExperience: Int,
    val bigFiveScores: BigFiveScores? = null,
    val availability: List<TimeSlot> = emptyList()
)