package com.rsicarelli.homehunt_kmm.data.repository

import com.rsicarelli.homehunt_kmm.data.cache.SearchOptionCache
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.rsicarelli.homehunt_kmm.domain.repository.SearchOptionRepository
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SettingsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalSettingsApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class SearchOptionRepositoryImpl(
    private val searchOptionCache: SearchOptionCache
) : SearchOptionRepository {

    init {
        callbackFlow<SearchOption> {
            _searchOptions.tryEmit(searchOptionCache.get())

            val listen = searchOptionCache.listen {
                _searchOptions.tryEmit(searchOptionCache.get())
            }

            awaitClose {
                listen.deactivate()
            }
        }.launchIn(CoroutineScope(Dispatchers.Main))
    }

    override fun listen(callback: () -> Unit): SettingsListener {
        return searchOptionCache.listen(callback)
    }

    private val _searchOptions = MutableSharedFlow<SearchOption>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val searchOptions = _searchOptions

    override fun get() = searchOptionCache.get()

    override fun save(searchOption: SearchOption) {
        searchOptionCache.save(searchOption)
    }

}