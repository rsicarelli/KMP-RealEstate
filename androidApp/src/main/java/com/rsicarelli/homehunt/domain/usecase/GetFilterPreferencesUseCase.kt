package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.rsicarelli.homehunt_kmm.domain.repository.SearchOptionRepository
import kotlinx.coroutines.flow.flow

class GetFilterPreferencesUseCase(
    private val searchOptionRepository: SearchOptionRepository
) : UseCase<Unit, GetFilterPreferencesUseCase.Outcome> {

    override fun invoke(request: Unit) = flow {
        emit(Outcome(searchOptionRepository.get()))
    }

    class Outcome(val searchOption: SearchOption)
}