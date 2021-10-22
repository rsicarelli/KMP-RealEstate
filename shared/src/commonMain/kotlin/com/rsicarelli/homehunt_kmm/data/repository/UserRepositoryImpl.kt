package com.rsicarelli.homehunt_kmm.data.repository

import com.rsicarelli.homehunt_kmm.data.cache.UserCache
import com.rsicarelli.homehunt_kmm.data.network.UserService
import com.rsicarelli.homehunt_kmm.domain.model.User
import com.rsicarelli.homehunt_kmm.domain.repository.UserRepository
import com.rsicarelli.homehunt_kmm.type.UserInput

class UserRepositoryImpl(
    private val userCache: UserCache,
    private val userService: UserService,
) : UserRepository {
    override suspend fun signIn(userInput: UserInput): User =
        userService.signIn(userInput).also {
            userCache.saveUser(it.id, it.token)
        }

    override suspend fun signUp(userInput: UserInput): User =
        userService.signUp(userInput).also {
            userCache.saveUser(it.id, it.token)
        }

    override suspend fun getUser(): User? = userCache.getUser()

    override suspend fun deleteUser() {
        userCache.deleteUser()
    }
}