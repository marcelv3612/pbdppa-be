// src/main/kotlin/com/bdcga/pbdpp/dto/TimeSlot.kt

package com.bdcga.pbdpp.dto

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class TimeSlot(
    val start: LocalDateTime,
    val end: LocalDateTime
)