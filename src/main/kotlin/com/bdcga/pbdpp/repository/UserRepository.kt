package com.bdcga.pbdpp.repository// package com.bdcga.pbdpp.repository

import com.bdcga.pbdpp.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<User, String> {
    fun existsByUsername(username: String): Mono<Boolean>
    fun existsByEmail(email: String): Mono<Boolean>
    fun findByUsernameOrEmail(username: String, email: String): Mono<User>
}