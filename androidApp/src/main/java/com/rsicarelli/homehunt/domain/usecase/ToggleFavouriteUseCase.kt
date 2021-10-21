package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.domain.repository.PropertyRepository
import kotlinx.coroutines.flow.flow

class ToggleFavouriteUseCase(
    private val propertyRepository: PropertyRepository
) : UseCase<ToggleFavouriteUseCase.Request, ToggleFavouriteUseCase.Outcome> {

    override operator fun invoke(request: Request) = flow {
        propertyRepository.toggleFavourite(request.referenceId, request.isFavourited)
        emit(Outcome(true))
    }

    data class Request(
        val referenceId: String,
        val isFavourited: Boolean
    )

    data class Outcome(val result: Boolean)
}