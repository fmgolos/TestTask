package fdr.home.task.service.authentification

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils

data class MyUser(val login: String) : AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {
    init {
        isAuthenticated = true
    }

    override fun getName(): String = login
    override fun getCredentials(): String = login
    override fun getDetails() = login
    override fun getPrincipal() = login

    override fun setDetails(details: Any?) {
        error("details property is immutable")
    }
}