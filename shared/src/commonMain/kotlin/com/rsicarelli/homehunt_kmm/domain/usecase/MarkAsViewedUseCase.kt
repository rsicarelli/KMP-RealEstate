package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.domain.usecase.MarkAsViewedUseCase.Outcome
import com.rsicarelli.homehunt_kmm.domain.usecase.MarkAsViewedUseCase.Request
import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import com.rsicarelli.homehunt_kmm.type.ViewedPropertyInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarkAsViewedUseCase(
    private val propertiesRepository: PropertyRepository
) : UseCase<Request, Outcome> {

    override operator fun invoke(request: Request): Flow<Outcome> = flow {
        propertiesRepository.markAsViewed(ViewedPropertyInput(request.referenceId))
        emit(Outcome(true))
    }

    data class Request(val referenceId: String)
    data class Outcome(val success: Boolean)
}