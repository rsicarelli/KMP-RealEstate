package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.domain.repository.PropertyRepository_Old
import com.rsicarelli.homehunt.domain.usecase.GetSinglePropertyUseCase.Outcome
import com.rsicarelli.homehunt.domain.usecase.GetSinglePropertyUseCase.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetSinglePropertyUseCase(
    private val propertiesRepositoryOld: PropertyRepository_Old
) : UseCase<Request, Outcome> {

    override operator fun invoke(request: Request): Flow<Outcome> =
        propertiesRepositoryOld.getActiveProperties()
            .filterNotNull()
            .map { properties -> properties.first { it._id == request.referenceId } }
            .map { Outcome(it) }

    class Request(val referenceId: String)
    data class Outcome(val property: Property)
}