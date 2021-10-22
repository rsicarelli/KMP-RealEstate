package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.domain.repository.PropertyRepository_Old
import com.rsicarelli.homehunt.domain.usecase.GetFavouritedPropertiesUseCase.Outcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetFavouritedPropertiesUseCase(
    private val propertiesRepositoryOld: PropertyRepository_Old
) : UseCase<Unit, Outcome> {

    override fun invoke(request: Unit): Flow<Outcome> = propertiesRepositoryOld.getActiveProperties()
        .filterNotNull()
        .map { properties ->
            Outcome(properties.filter { it.isUpVoted })
        }

    data class Outcome(val properties: List<Property>)
}