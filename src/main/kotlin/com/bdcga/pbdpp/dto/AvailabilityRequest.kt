// src/main/kotlin/com/bdcga/pbdpp/dto/AvailabilityRequest.kt

package com.bdcga.pbdpp.dto

import jakarta.validation.constraints.NotEmpty

data class AvailabilityRequest(
    @field:NotEmpty(message = "Availability list cannot be empty")
    val availability: List<TimeSlot>
)