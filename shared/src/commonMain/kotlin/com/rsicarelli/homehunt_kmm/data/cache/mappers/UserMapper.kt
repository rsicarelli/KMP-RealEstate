package com.rsicarelli.homehunt_kmm.data.cache.mappers

import com.rsicarelli.homehunt_kmm.SignInMutation
import com.rsicarelli.homehunt_kmm.SignUpMutation
import com.rsicarelli.homehunt_kmm.domain.model.User
import com.rsicarelli.homehuntkmm.datasource.cache.UserState

internal fun UserState.toUser() =
    User(id = userId, token = token)

internal fun SignInMutation.SignIn.toUser() = User(user._id, token)

internal fun SignUpMutation.SignUp.toUser() = User(user._id, token)