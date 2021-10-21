package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.domain.repository.PropertyRepository
import com.rsicarelli.homehunt.domain.repository.UserRepository
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase.Outcome
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarkAsViewedUseCase(
    private val propertiesRepository: PropertyRepository,
    private val userRepository: UserRepository
) : UseCase<Request, Outcome> {

    override operator fun invoke(request: Request): Flow<Outcome> = flow {
        propertiesRepository.markAsViewed(request.referenceId, userRepository.getUserId())
        emit(Outcome(true))
    }

    data class Request(
        val referenceId: String
    )

    data class Outcome(val success: Boolean)
}