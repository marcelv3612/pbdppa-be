package com.bdcga.pbdpp.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "sessions")
data class Session(
    @Id
    val sessionId: String? = null,

    val userId: String,

    val role: String, // "pilot" or "navigator"

    val startTime: LocalDateTime = LocalDateTime.now()
)