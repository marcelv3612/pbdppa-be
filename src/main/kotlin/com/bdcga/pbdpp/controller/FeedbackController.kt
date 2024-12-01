package com.bdcga.pbdpp.controller// package com.bdcga.pbdpp.controller

import com.bdcga.pbdpp.dto.FeedbackRequest
import com.bdcga.pbdpp.service.FeedbackService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/feedback")
class FeedbackController(
    private val feedbackService: FeedbackService
) {

    @PostMapping
    suspend fun submitFeedback(@Valid @RequestBody feedbackRequest: FeedbackRequest): ResponseEntity<Any> {
        return feedbackService.submitFeedback(feedbackRequest)
    }
}