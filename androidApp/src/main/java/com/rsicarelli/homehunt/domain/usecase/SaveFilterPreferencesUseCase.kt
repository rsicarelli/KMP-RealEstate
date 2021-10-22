package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt.data.datasource.FilterLocalDataSource
import com.rsicarelli.homehunt.domain.model.SearchOption
import com.rsicarelli.homehunt.domain.usecase.SaveFilterPreferencesUseCase.Outcome
import com.rsicarelli.homehunt.domain.usecase.SaveFilterPreferencesUseCase.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SaveFilterPreferencesUseCase(
    private val filterLocalDataSource: FilterLocalDataSource
) : UseCase<Request, Outcome> {

    override operator fun invoke(request: Request) =
        flow {
            filterLocalDataSource.save(request.searchOption)
            emit(Outcome(true))
        }.flowOn(Dispatchers.IO)

    data class Request(
        val searchOption: SearchOption
    )

    data class Outcome(val result: Boolean)
}