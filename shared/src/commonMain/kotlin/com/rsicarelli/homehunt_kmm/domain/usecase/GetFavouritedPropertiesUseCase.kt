package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.domain.usecase.GetFavouritedPropertiesUseCase.Outcome
import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import kotlinx.coroutines.flow.*

class GetFavouritedPropertiesUseCase(
    private val propertiesRepository: PropertyRepository
) : UseCase<Unit, Outcome> {

    override fun invoke(request: Unit): Flow<Outcome> = flow {
        val cachedProperties = propertiesRepository.getFavourites()
        emit(Outcome(cachedProperties)).also {
            val updatedFavourites = propertiesRepository.fetchFavourites()
            emit(Outcome(updatedFavourites))
        }
    }

    data class Outcome(val properties: List<Property>)
}