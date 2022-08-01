package fdr.home.task.config

import fdr.home.task.service.authentification.AuthFilter
import fdr.home.task.service.authentification.TokenService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val tokenService: TokenService) : WebSecurityConfigurerAdapter() {

    val SWAGGER_WHITE_LIST = arrayOf(
        "/v3/api-docs.yaml",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**",
    )

    private val PROTECTED_URLS: RequestMatcher = AntPathRequestMatcher("/api/**")

    override fun configure(webSecurity: WebSecurity) {
        webSecurity.ignoring()
            .antMatchers(*SWAGGER_WHITE_LIST)
            .antMatchers("/login")
    }


    override fun configure(http: HttpSecurity) {
        http
            .addFilterBefore(
                AuthFilter(PROTECTED_URLS, tokenService),
                AnonymousAuthenticationFilter::class.java
            )
            .authorizeRequests()
            .requestMatchers(PROTECTED_URLS).authenticated()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
            .sessionManagement().disable()
    }
}