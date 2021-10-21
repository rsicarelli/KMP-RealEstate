package com.rsicarelli.homehunt.domain.repository

interface UserRepository {
    fun getUserId(): String
    fun isLoggedIn(): Boolean
}