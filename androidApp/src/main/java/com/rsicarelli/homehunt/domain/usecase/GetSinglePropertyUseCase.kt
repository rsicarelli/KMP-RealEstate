package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.domain.model.Property
import com.rsicarelli.homehunt.domain.repository.PropertyRepository
import com.rsicarelli.homehunt.domain.usecase.GetSinglePropertyUseCase.Outcome
import com.rsicarelli.homehunt.domain.usecase.GetSinglePropertyUseCase.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetSinglePropertyUseCase(
    private val propertiesRepository: PropertyRepository
) : UseCase<Request, Outcome> {

    override operator fun invoke(request: Request): Flow<Outcome> =
        propertiesRepository.getActiveProperties()
            .filterNotNull()
            .map { properties -> properties.first { it.reference == request.referenceId } }
            .map { Outcome(it) }

    class Request(val referenceId: String)
    data class Outcome(val property: Property)
}