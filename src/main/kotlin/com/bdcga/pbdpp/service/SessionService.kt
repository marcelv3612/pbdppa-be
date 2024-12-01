package com.bdcga.pbdpp.service// package com.bdcga.pbdpp.service

import com.bdcga.pbdpp.dto.ApiResponse
import com.bdcga.pbdpp.dto.SessionRequest
import com.bdcga.pbdpp.model.Session
import com.bdcga.pbdpp.repository.SessionRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SessionService(
    private val sessionRepository: SessionRepository
) {

    suspend fun startSession(sessionRequest: SessionRequest): ResponseEntity<Any> {
        val session = Session(
            sessionId = sessionRequest.sessionId,
            userId = sessionRequest.userId,
            role = sessionRequest.role
        )
        sessionRepository.save(session).awaitFirstOrNull()
        return ResponseEntity.ok(ApiResponse(true, "Session started", mapOf("role" to session.role)))
    }
}