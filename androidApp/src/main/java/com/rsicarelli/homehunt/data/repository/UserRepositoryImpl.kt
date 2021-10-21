package com.rsicarelli.homehunt.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.rsicarelli.homehunt.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
    override fun getUserId() = firebaseAuth.currentUser?.uid ?: error("Current user is null")

    override fun isLoggedIn() = firebaseAuth.currentUser != null
}