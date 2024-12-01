// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.security

import com.bdcga.pbdpp.repository.UserRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> {
        return userRepository.findByUsernameOrEmail(username, username)
            .map { UserPrincipal.create(it) }
    }

    fun loadUserById(id: String): Mono<UserDetails> {
        return userRepository.findById(id)
            .map { UserPrincipal.create(it) }
    }
}