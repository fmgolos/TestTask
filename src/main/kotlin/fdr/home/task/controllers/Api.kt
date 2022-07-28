package fdr.home.task.controllers

import fdr.home.task.database.user.credentials.PostgresUserCredentialsStorage
import fdr.home.task.database.user.credentials.UserCredentialsRequest
import fdr.home.task.service.UserCredentialValidator
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Api(private val userCredentialsStorage: PostgresUserCredentialsStorage) {
    @PostMapping("/validate")
    fun addUser(@RequestBody userCredentialsRequest: UserCredentialsRequest): String {
        return UserCredentialValidator(userCredentialsStorage).validate(
            userCredentialsRequest.login,
            userCredentialsRequest.password
        )
    }
}