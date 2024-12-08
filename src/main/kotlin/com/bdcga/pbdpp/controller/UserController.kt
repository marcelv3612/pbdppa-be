package com.bdcga.pbdpp.controller// package com.bdcga.pbdpp.controller

import com.bdcga.pbdpp.dto.*
import com.bdcga.pbdpp.security.UserPrincipal
import com.bdcga.pbdpp.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal

@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService
) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/profile/me/availability")
    suspend fun updateAvailability(
        @Valid @RequestBody availabilityRequest: AvailabilityRequest,
        @AuthenticationPrincipal currentUser: UserPrincipal
    ): ResponseEntity<Any> {
        return userService.updateAvailability(currentUser.id, availabilityRequest)
    }

    @PostMapping("/profile/me/bigfive")
    suspend fun updateBigFiveScores(
        @Valid @RequestBody bigFiveScoresRequest: BigFiveScoresRequest,
        @AuthenticationPrincipal currentUser: UserPrincipal
    ): ResponseEntity<Any> {
        return userService.updateBigFiveScores(currentUser.id, bigFiveScoresRequest)
    }

    @PostMapping("/register")
    suspend fun registerUser(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<AuthResponse> {
        logger.info("Received registration request: $registerRequest")
        return userService.registerUser(registerRequest)
    }

    @PostMapping("/authenticate")
    suspend fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<AuthResponse> {
        logger.info("Received authentication request: $loginRequest")
        return userService.authenticateUser(loginRequest)
    }

    @GetMapping("/profile/{userId}")
    suspend fun getUserProfile(@PathVariable userId: String): ResponseEntity<Any> {
        logger.info("Received request for user profile: $userId")
        return userService.getUserProfile(userId)
    }
}