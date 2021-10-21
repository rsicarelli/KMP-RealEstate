package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.data.datasource.FilterLocalDataSource
import com.rsicarelli.homehunt.domain.model.SearchOption
import kotlinx.coroutines.flow.flow

class GetFilterPreferencesUseCase(
    private val filterLocalDataSource: FilterLocalDataSource
) : UseCase<Unit, GetFilterPreferencesUseCase.Outcome> {

    override fun invoke(request: Unit) = flow {
        emit(Outcome(filterLocalDataSource.get()))
    }

    class Outcome(val searchOption: SearchOption)
}