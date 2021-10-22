package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.domain.usecase.GetFavouritedPropertiesUseCase.Outcome
import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetFavouritedPropertiesUseCase(
    private val propertiesRepository: PropertyRepository
) : UseCase<Unit, Outcome> {

    override fun invoke(request: Unit): Flow<Outcome> = propertiesRepository.getProperties()
        .filterNotNull()
        .map { properties ->
            Outcome(properties.filter { it.isUpVoted })
        }

    data class Outcome(val properties: List<Property>)
}