package fdr.home.task.service.authentification

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthFilter(
    requestMatcher: RequestMatcher,
    private val tokenService: TokenService
) : AbstractAuthenticationProcessingFilter(requestMatcher) {

    init {
        this.setAuthenticationSuccessHandler { _, _, _ -> }
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val token = extractTokenFromRequest(request)
        val tokenWithoutBearerPrefix = token.replace("Bearer_", "")
        if (tokenService.isValid(tokenWithoutBearerPrefix)) {
            return MyUser("your token here")
        } else {
            throw AuthenticationCredentialsNotFoundException("No Authorization header found")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
        chain.doFilter(request, response)
    }

    @Throws(AuthenticationCredentialsNotFoundException::class)
    private fun extractTokenFromRequest(request: HttpServletRequest): String {
        return request.getHeader("Authorization")
            ?: throw AuthenticationCredentialsNotFoundException("No Authorization header found")
    }
}