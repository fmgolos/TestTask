package fdr.home.task.controllers.user


import fdr.home.task.database.user.storage.PostgresUserStorage
import fdr.home.task.service.authentification.Authentication
import mu.KLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserControllers(
    private val userCredentialsStorage: PostgresUserStorage,
    private val authentication: Authentication
) {
    @PostMapping("/create")
    fun createNewUser(@RequestBody pojoLoginPassword: PojoLoginPassword) {
        userCredentialsStorage.createNewUser(pojoLoginPassword)
    }

    @PostMapping("/login")
    fun login(@RequestBody pojoLoginPassword: PojoLoginPassword): AuthResponse {
        val token = authentication.login(pojoLoginPassword.login, pojoLoginPassword.password)
        return AuthResponse(token)
    }

    private companion object : KLogging()
}

data class AuthResponse(val token: String)