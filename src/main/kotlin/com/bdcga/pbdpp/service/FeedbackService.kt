package com.bdcga.pbdpp.service// package com.bdcga.pbdpp.service

import com.bdcga.pbdpp.dto.ApiResponse
import com.bdcga.pbdpp.dto.FeedbackRequest
import com.bdcga.pbdpp.model.Feedback
import com.bdcga.pbdpp.repository.FeedbackRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    private val feedbackRepository: FeedbackRepository
) {

    suspend fun submitFeedback(feedbackRequest: FeedbackRequest): ResponseEntity<Any> {
        if (feedbackRequest.feedback.length > 500) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(ApiResponse(false, "Feedback exceeds the allowed character limit"))
        }

        val feedback = Feedback(
            sessionId = feedbackRequest.sessionId,
            userId = feedbackRequest.userId,
            feedback = feedbackRequest.feedback
        )
        feedbackRepository.save(feedback).awaitFirstOrNull()
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse(true, "Feedback recorded successfully"))
    }
}