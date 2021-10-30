package com.rsicarelli.homehunt_kmm.data.repository

import com.rsicarelli.homehunt_kmm.data.cache.PropertyCache
import com.rsicarelli.homehunt_kmm.data.network.PropertyService
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import com.rsicarelli.homehunt_kmm.type.DownVoteInput
import com.rsicarelli.homehunt_kmm.type.UpVoteInput
import com.rsicarelli.homehunt_kmm.type.ViewedPropertyInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
class PropertyRepositoryImpl(
    private val propertyCache: PropertyCache,
    private val propertyService: PropertyService
) : PropertyRepository {

    private val _properties = MutableSharedFlow<List<Property>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        propertyCache.properties.distinctUntilChanged().onEach {
            _properties.tryEmit(it)
        }.launchIn(CoroutineScope(Dispatchers.Default))

        CoroutineScope(Dispatchers.Default).launch {
            propertyService.getAllProperties()?.let { properties ->
                propertyCache.saveAll(properties)
            }
        }
    }

    override fun getDiscover(): Flow<List<Property>> = flow {
        propertyService.getRecommendations()?.let {
            emit(it)
        }
    }

    override val properties: Flow<List<Property>> = _properties.distinctUntilChanged()

    override fun getAll(): Flow<List<Property>> = flow { emit(propertyCache.getAll()) }

    override suspend fun getFavourites(): List<Property> =
        propertyCache.getAll().filter { it.isUpVoted }

    override suspend fun fetchFavourites(): List<Property> {
        return getFavourites()
    }

    override suspend fun getPropertyById(id: String): Property? = propertyCache.get(id)

    override suspend fun markAsViewed(viewedPropertyInput: ViewedPropertyInput) {
        propertyService.markAsViewed(viewedPropertyInput)
            .takeIf { success -> success }
            ?.let { propertyCache.updateVisibility(viewedPropertyInput.propertyId) }
    }

    override suspend fun upVote(upVoteInput: UpVoteInput) {
        propertyService.upVote(upVoteInput)
            .takeIf { success -> success }
            ?.let { propertyCache.upVote(upVoteInput.propertyId) }
    }

    override suspend fun downVote(downVoteInput: DownVoteInput) {
        propertyService.downVote(downVoteInput)
            .takeIf { success -> success }
            ?.let { propertyCache.downVote(downVoteInput.propertyId) }
    }
}