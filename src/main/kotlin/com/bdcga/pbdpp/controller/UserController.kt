package com.bdcga.pbdpp.controller// package com.bdcga.pbdpp.controller

import com.bdcga.pbdpp.dto.*
import com.bdcga.pbdpp.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    suspend fun registerUser(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
        return userService.registerUser(registerRequest)
    }

    @PostMapping("/authenticate")
    suspend fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<AuthResponse> {
        return userService.authenticateUser(loginRequest)
    }

    @GetMapping("/profile/{userId}")
    suspend fun getUserProfile(@PathVariable userId: String): ResponseEntity<Any> {
        return userService.getUserProfile(userId)
    }
}