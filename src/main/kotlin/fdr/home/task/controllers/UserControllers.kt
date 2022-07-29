package fdr.home.task.controllers

import fdr.home.task.user.storage.PostgresUserStorage
import fdr.home.task.user.storage.UserCredentials
import fdr.home.task.user.storage.UserCredentialsRequest
import fdr.home.task.service.authentification.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException

@RestController
class UserControllers(private val userCredentialsStorage: PostgresUserStorage) {
    @PostMapping("/login")
    fun login(@RequestBody userCredentialsRequest: UserCredentialsRequest): AuthResponse {
        val token = Authentication(userCredentialsStorage).login(
            userCredentialsRequest.login,
            userCredentialsRequest.password
        )
        return AuthResponse(token)
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody user: UserCredentials) {
        if (userCredentialsStorage.isExist(user.login, user.password)) {
            userCredentialsStorage.delete(user.id)
        } else throw IllegalArgumentException("user with ${user.id} does not exist")
    }
}

data class AuthResponse(val token: String)