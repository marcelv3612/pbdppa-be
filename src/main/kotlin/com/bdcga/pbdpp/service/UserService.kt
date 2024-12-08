package com.bdcga.pbdpp.service// package com.bdcga.pbdpp.service

import com.bdcga.pbdpp.dto.*
import com.bdcga.pbdpp.model.BigFiveScores
import com.bdcga.pbdpp.model.User
import com.bdcga.pbdpp.repository.UserRepository
import com.bdcga.pbdpp.security.JwtTokenProvider
import com.bdcga.pbdpp.security.UserPrincipal
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

private const val USER_NOT_FOUND = "User not found"

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    suspend fun registerUser(request: RegisterRequest): ResponseEntity<AuthResponse> {
        logger.info("Attempting to register user: ${request.username}")

        // Check if username or email already exists
        if (userRepository.existsByUsername(request.username).awaitFirstOrNull() == true) {
            logger.warn("Username already taken: ${request.username}")
            return ResponseEntity.badRequest().body(AuthResponse(error = "Username is already taken!"))
        }
        if (userRepository.existsByEmail(request.email).awaitFirstOrNull() == true) {
            logger.warn("Email already in use: ${request.email}")
            return ResponseEntity.badRequest().body(AuthResponse(error = "Email Address already in use!"))
        }

        // Create new user
        val user = User(
            username = request.username,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
            gender = request.gender,
            softwareExperience = request.softwareExperience,
        )
        val savedUser = userRepository.save(user).awaitFirstOrNull()
        logger.info("User registered successfully with ID: ${savedUser?.id}")

        // Generate JWT token
        val userPrincipal = UserPrincipal.create(savedUser!!)
        val jwt = jwtTokenProvider.generateToken(userPrincipal)

        // Return token in the response
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthResponse(token = jwt))
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
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(false, USER_NOT_FOUND))
        return ResponseEntity.ok(user)
    }

    suspend fun updateBigFiveScores(userId: String, request: BigFiveScoresRequest): ResponseEntity<Any> {
        val user = userRepository.findById(userId).awaitFirstOrNull()
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(false, USER_NOT_FOUND))

        val updatedUser = user.copy(
            bigFiveScores = BigFiveScores(
                extraversion = request.extraversion,
                agreeableness = request.agreeableness,
                conscientiousness = request.conscientiousness,
                neuroticism = request.neuroticism,
                openness = request.openness
            )
        )

        userRepository.save(updatedUser).awaitFirstOrNull()
        return ResponseEntity.ok(ApiResponse(true, "BigFive scores updated successfully"))
    }

    suspend fun updateAvailability(userId: String, request: AvailabilityRequest): ResponseEntity<Any> {
        logger.info("Updating availability for user ID: $userId")

        val user = userRepository.findById(userId).awaitFirstOrNull()
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse(false, USER_NOT_FOUND))

        val updatedUser = user.copy(
            availability = request.availability
        )

        userRepository.save(updatedUser).awaitFirstOrNull()
        logger.info("Availability updated for user ID: $userId")
        return ResponseEntity.ok(ApiResponse(true, "Availability updated successfully"))
    }
}