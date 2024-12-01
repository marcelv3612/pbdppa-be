package com.bdcga.pbdpp.dto// package com.bdcga.pbdpp.dto

data class SessionRequest(
    val sessionId: String,
    val userId: String,
    val role: String // "solo", "pilot", or "navigator"
)