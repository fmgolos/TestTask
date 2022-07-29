package fdr.home.task.controllers.user

import fdr.home.task.web.exceptions.UnAuthorizedException
import fdr.home.task.database.user.storage.PostgresUserStorage
import fdr.home.task.database.user.storage.UserCredentialsRequest
import fdr.home.task.service.authentification.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserControllers(private val userCredentialsStorage: PostgresUserStorage) {
    @PostMapping("/login")
    fun login(@RequestBody userCredentialsRequest: UserCredentialsRequest): AuthResponse {
        if (userCredentialsStorage.isExist(userCredentialsRequest.login,userCredentialsRequest.password)){
            val token = Authentication(userCredentialsStorage).login(
                userCredentialsRequest.login,
                userCredentialsRequest.password
            )
            return AuthResponse(token)
        }
        else throw UnAuthorizedException()

    }

//    @DeleteMapping("/delete")
//    fun delete(@RequestBody user: UserCredentialsRequest) {
//        if (userCredentialsStorage.isExist(user.login, user.password)) {
//            userCredentialsStorage.delete(user.id)
//        } else throw IllegalArgumentException("user with ${user.id} does not exist")
//    }
}

data class AuthResponse(val token: String)