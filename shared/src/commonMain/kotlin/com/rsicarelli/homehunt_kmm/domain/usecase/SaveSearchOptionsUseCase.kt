package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.rsicarelli.homehunt_kmm.domain.usecase.SaveSearchOptionsUseCase.Outcome
import com.rsicarelli.homehunt_kmm.domain.usecase.SaveSearchOptionsUseCase.Request
import com.rsicarelli.homehunt_kmm.domain.repository.SearchOptionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SaveSearchOptionsUseCase(
    private val searchOptionRepository: SearchOptionRepository
) : UseCase<Request, Outcome> {

    override operator fun invoke(request: Request) =
        flow {
            searchOptionRepository.save(request.searchOption)
            emit(Outcome(true))
        }.flowOn(Dispatchers.Default) //TODO: proper handle threading on kmm

    data class Request(
        val searchOption: SearchOption
    )

    data class Outcome(val result: Boolean)
}