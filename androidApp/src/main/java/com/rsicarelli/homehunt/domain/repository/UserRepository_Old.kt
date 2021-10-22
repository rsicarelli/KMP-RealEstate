package com.rsicarelli.homehunt.domain.repository

interface UserRepository_Old {
    fun getUserId(): String
    fun isLoggedIn(): Boolean
}