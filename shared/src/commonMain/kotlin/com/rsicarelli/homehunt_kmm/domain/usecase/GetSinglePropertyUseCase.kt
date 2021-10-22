package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.domain.usecase.GetSinglePropertyUseCase.Outcome
import com.rsicarelli.homehunt_kmm.domain.usecase.GetSinglePropertyUseCase.Request
import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSinglePropertyUseCase(
    private val propertiesRepository: PropertyRepository
) : UseCase<Request, Outcome> {

    override operator fun invoke(request: Request): Flow<Outcome> = flow {
        val property = propertiesRepository.getPropertyById(request.referenceId)
        property?.let {
            emit(Outcome(it))
        } ?: error("Property not found") //TODO: Emit outcome as error instead
    }


    class Request(val referenceId: String)
    data class Outcome(val property: Property)
}