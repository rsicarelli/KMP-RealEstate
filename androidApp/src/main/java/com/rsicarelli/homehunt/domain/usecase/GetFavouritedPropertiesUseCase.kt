package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.domain.repository.PropertyRepository
import com.rsicarelli.homehunt.domain.usecase.GetFavouritedPropertiesUseCase.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetFavouritedPropertiesUseCase(
    private val propertiesRepository: PropertyRepository
) : UseCase<Unit, Outcome> {

    override fun invoke(request: Unit): Flow<Outcome> = propertiesRepository.getActiveProperties()
        .filterNotNull()
        .map { properties ->
            Outcome(properties.filter { it.isFavourited })
        }

    data class Outcome(val properties: List<Property>)
}