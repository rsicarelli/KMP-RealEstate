package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.domain.repository.PropertyRepository_Old
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class GetFilteredPropertiesUseCase(
    private val propertiesRepositoryOld: PropertyRepository_Old,
    private val getFilterPreferences: GetFilterPreferencesUseCase,
    private val filterProperties: FilterPropertiesUseCase,
) : UseCase<Unit, GetFilteredPropertiesUseCase.Outcome> {

    data class Outcome(val properties: List<Property>)

    @OptIn(FlowPreview::class)
    override fun invoke(request: Unit): Flow<Outcome> =
        propertiesRepositoryOld.getActiveProperties()
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