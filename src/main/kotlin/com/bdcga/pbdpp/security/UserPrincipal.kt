// package com.bdcga.pbdpp.security

package com.bdcga.pbdpp.security

import com.bdcga.pbdpp.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
    val id: String,
    private val username: String,
    val email: String,
    private val password: String
) : UserDetails {

    companion object {
        fun create(user: User): UserPrincipal {
            return UserPrincipal(
                id = user.id ?: "",
                username = user.username,
                email = user.email,
                password = user.passwordHash
            )
        }
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        // Assuming all users have the same role; adjust if needed
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}