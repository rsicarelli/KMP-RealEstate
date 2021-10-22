package com.rsicarelli.homehunt_kmm.datasource.cache.mappers

import com.rsicarelli.homehunt_kmm.domain.model.User
import com.rsicarelli.homehuntkmm.datasource.cache.UserState

internal fun UserState.toUser() =
    User(id = userId, token = token)

