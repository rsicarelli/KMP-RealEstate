package com.rsicarelli.homehunt_kmm.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.rsicarelli.homehunt_kmm.domain.repository.SearchOptionRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetSearchOptionSettings(
    private val searchOptionRepository: SearchOptionRepository
) : UseCase<Unit, GetSearchOptionSettings.Outcome> {

    override fun invoke(request: Unit) = searchOptionRepository.searchOptions.map { Outcome(it) }

    class Outcome(val searchOption: SearchOption)
}