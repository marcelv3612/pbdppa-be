package com.bdcga.pbdpp.model

import org.springframework.data.mongodb.core.mapping.Document
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

@Document(collection = "feedback")
data class Feedback(
    @Id
    val id: String? = null,

    val sessionId: String,

    val userId: String,

    @field:Size(max = 500)
    val feedback: String,

    val timestamp: LocalDateTime = LocalDateTime.now()
)