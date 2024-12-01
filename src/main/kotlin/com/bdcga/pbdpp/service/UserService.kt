package com.bdcga.pbdpp.service// package com.bdcga.pbdpp.service

import com.bdcga.pbdpp.dto.*
import com.bdcga.pbdpp.model.User
import com.bdcga.pbdpp.repository.UserRepository
import com.bdcga.pbdpp.security.JwtTokenProvider
import com.bdcga.pbdpp.security.UserPrincipal
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    suspend fun registerUser(request: RegisterRequest): ResponseEntity<Any> {
        // Check if username or email already exists
        if (userRepository.existsByUsername(request.username).awaitFirstOrNull() == true) {
            return ResponseEntity.badRequest().body(ApiResponse(false, "Username is already taken!"))
        }
        if (userRepository.existsByEmail(request.email).awaitFirstOrNull() == true) {
            return ResponseEntity.badRequest().body(ApiResponse(false, "Email Address already in use!"))
        }

        // Create new user
        val user = User(
            username = request.username,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            bigFiveScores = request.bigFiveScores,
            availability = request.availability
        )
        val result = userRepository.save(user).awaitFirstOrNull()
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse(true, result?.id ?: ""))
    }

    suspend fun authenticateUser(loginRequest: LoginRequest): ResponseEntity<AuthResponse> {
        val user = userRepository.findByUsernameOrEmail(loginRequest.usernameOrEmail, loginRequest.usernameOrEmail)
            .awaitFirstOrNull()

        if (user == null || !passwordEncoder.matches(loginRequest.password, user.passwordHash)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
        }

        val userPrincipal = UserPrincipal.create(user)
        val jwt = jwtTokenProvider.generateToken(userPrincipal)
        return ResponseEntity.ok(AuthResponse(jwt))
    }

    suspend fun getUserProfile(userId: String): ResponseEntity<Any> {
        val user = userRepository.findById(userId).awaitFirstOrNull()
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(false, "User not found"))
        return ResponseEntity.ok(user)
    }
}