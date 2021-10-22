package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.domain.repository.PropertyRepository_Old
import com.rsicarelli.homehunt.domain.repository.UserRepository_Old
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase.Outcome
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarkAsViewedUseCase(
    private val propertiesRepositoryOld: PropertyRepository_Old,
    private val userRepositoryOld: UserRepository_Old
) : UseCase<Request, Outcome> {

    override operator fun invoke(request: Request): Flow<Outcome> = flow {
        propertiesRepositoryOld.markAsViewed(request.referenceId, userRepositoryOld.getUserId())
        emit(Outcome(true))
    }

    data class Request(
        val referenceId: String
    )

    data class Outcome(val success: Boolean)
}