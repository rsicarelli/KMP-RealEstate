package com.rsicarelli.homehunt_kmm.domain.model

data class User(
    val id: String,
    val token: String
) {

    companion object {
        const val MIN_USERNAME_LENGTH = 4
        const val MAX_USERNAME_LENGTH = 10

        const val MIN_PASSWORD_LENGTH = 6
        const val MAX_PASSWORD_LENGTH = 16
    }
}