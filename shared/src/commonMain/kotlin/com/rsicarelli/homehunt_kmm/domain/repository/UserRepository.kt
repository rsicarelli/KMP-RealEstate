package com.rsicarelli.homehunt_kmm.domain.repository

import com.rsicarelli.homehunt_kmm.domain.model.User
import com.rsicarelli.homehunt_kmm.type.UserInput

interface UserRepository {
    suspend fun signIn(userInput: UserInput): User
    suspend fun signUp(userInput: UserInput): User
    suspend fun getUser(): User?
    suspend fun deleteUser()
}
