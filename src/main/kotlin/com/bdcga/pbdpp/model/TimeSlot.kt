package com.bdcga.pbdpp.model

import java.time.LocalDateTime

data class TimeSlot(
    val start: LocalDateTime,
    val end: LocalDateTime
)