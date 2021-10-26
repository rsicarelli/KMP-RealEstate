package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.repository.PropertyRepository
import com.rsicarelli.homehunt_kmm.type.DownVoteInput
import com.rsicarelli.homehunt_kmm.type.UpVoteInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ToggleFavouriteUseCase(
    private val propertyRepository: PropertyRepository
) : UseCase<ToggleFavouriteUseCase.Request, ToggleFavouriteUseCase.Outcome> {

    override operator fun invoke(request: Request) = flow {
        val (referenceId, isUpVoted) = request

        if (isUpVoted) {
            propertyRepository.upVote(UpVoteInput(referenceId))
        } else {
            propertyRepository.downVote(DownVoteInput(propertyId = referenceId))
        }

        emit(Outcome(true)) //TODO: better handle error
    }.flowOn(Dispatchers.Default)

    data class Request(
        val referenceId: String,
        val isUpVoted: Boolean
    )

    data class Outcome(val result: Boolean)
}