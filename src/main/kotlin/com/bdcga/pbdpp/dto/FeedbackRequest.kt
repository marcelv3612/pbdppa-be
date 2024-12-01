package com.bdcga.pbdpp.dto// package com.bdcga.pbdpp.dto

import jakarta.validation.constraints.Size

data class FeedbackRequest(
    val sessionId: String,
    val userId: String,

    @field:Size(max = 500)
    val feedback: String
)