package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import com.rsicarelli.homehunt_kmm.type.RatingInput
import kotlinx.coroutines.flow.flow

class ToggleFavouriteUseCase(
    private val propertyRepository: PropertyRepository
) : UseCase<ToggleFavouriteUseCase.Request, ToggleFavouriteUseCase.Outcome> {

    override operator fun invoke(request: Request) = flow {
        val (referenceId, isUpVoted) = request
        propertyRepository.toggleRatings(
            RatingInput(
                isUpVoted = isUpVoted,
                propertyId = referenceId
            )
        )

        emit(Outcome(true))
    }

    data class Request(
        val referenceId: String,
        val isUpVoted: Boolean
    )

    data class Outcome(val result: Boolean)
}