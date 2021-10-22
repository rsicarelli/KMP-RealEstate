package com.rsicarelli.homehunt_kmm.data.cache

import com.rsicarelli.homehunt_kmm.data.cache.mappers.toUser
import com.rsicarelli.homehunt_kmm.domain.model.User

interface UserCache {
    fun getUser(): User?
    fun saveUser(userId: String, token: String)
    fun deleteUser()
}

class UserCacheImpl(homeHuntDatabase: HomeHuntDatabase) : UserCache {

    private val queries = homeHuntDatabase.homeHuntQueries

    override fun getUser(): User? = queries.selectUserState().executeAsOneOrNull()?.toUser()

    override fun saveUser(userId: String, token: String) {
        queries.transaction {
            queries.insertUserState(userId, token)
        }
    }

    override fun deleteUser() {
        queries.transaction {
            queries.removeUserState()
        }
    }

}