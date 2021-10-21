package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt.core.model.UseCase
import com.rsicarelli.homehunt.domain.model.Property
import com.rsicarelli.homehunt.domain.repository.PropertyRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class GetFilteredPropertiesUseCase(
    private val propertiesRepository: PropertyRepository,
    private val getFilterPreferences: GetFilterPreferencesUseCase,
    private val filterProperties: FilterPropertiesUseCase,
) : UseCase<Unit, GetFilteredPropertiesUseCase.Outcome> {

    data class Outcome(val properties: List<Property>)

    @OptIn(FlowPreview::class)
    override fun invoke(request: Unit): Flow<Outcome> =
        propertiesRepository.getActiveProperties()
            .filterNotNull()
            .combine(getFilterPreferences(request)) { properties, filterOutcome ->
                Pair(filterOutcome.searchOption, properties)
            }
            .flatMapConcat {
                filterProperties(FilterPropertiesUseCase.Request(it.first, it.second))
            }
            .map {
                Outcome(it.properties)
            }

}