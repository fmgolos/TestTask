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
    @PostMapping("/login")
    fun login(@RequestBody userCredentialsRequest: UserCredentialsRequest): AuthResponse {
        val token = authentication.login(userCredentialsRequest.login, userCredentialsRequest.password)
        return AuthResponse(token)
    }

    @PostMapping("/create")
    fun createNewUser(@RequestBody userCredentialsRequest: UserCredentialsRequest) {
        userCredentialsStorage.createNewUser(userCredentialsRequest)
    }

    private companion object : KLogging()
}

data class AuthResponse(val token: String)