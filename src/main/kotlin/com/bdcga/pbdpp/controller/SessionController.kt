package com.bdcga.pbdpp.controller// package com.bdcga.pbdpp.controller

import com.bdcga.pbdpp.dto.SessionRequest
import com.bdcga.pbdpp.service.SessionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sessions")
class SessionController(
    private val sessionService: SessionService
) {

    @PostMapping("/start")
    suspend fun startSession(@RequestBody sessionRequest: SessionRequest): ResponseEntity<Any> {
        return sessionService.startSession(sessionRequest)
    }
}