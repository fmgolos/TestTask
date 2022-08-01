package fdr.home.task.controllers.user

import fdr.home.task.database.user.storage.PostgresUserStorage
import fdr.home.task.service.authentification.Authentication
import fdr.home.task.web.exceptions.UnAuthorizedException
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
        if (userCredentialsStorage.userIsExist(userCredentialsRequest.login)) {
            val token = authentication.login(userCredentialsRequest.login, userCredentialsRequest.password)
            return AuthResponse(token)
        } else {
            logger.info { "Access denied because password is wrong" }
            throw UnAuthorizedException()
        }

    }

    @PostMapping("/create")
    fun createNewUser(@RequestBody userCredentialsRequest: UserCredentialsRequest) {
        userCredentialsStorage.createNewUser(userCredentialsRequest)
    }

    //    @DeleteMapping("/delete")
//    fun delete(@RequestBody user: UserCredentialsRequest) {
//        if (userCredentialsStorage.isExist(user.login, user.password)) {
//            userCredentialsStorage.delete(user.id)
//        } else throw IllegalArgumentException("user with ${user.id} does not exist")
//    }
    private companion object : KLogging()
}

data class AuthResponse(val token: String)