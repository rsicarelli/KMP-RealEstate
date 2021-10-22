package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.model.SearchOption
import com.rsicarelli.homehunt.domain.repository.PropertyRepository_Old
import com.rsicarelli.homehunt.domain.usecase.PreviewFilterResultUseCase.Outcome
import com.rsicarelli.homehunt.domain.usecase.PreviewFilterResultUseCase.Request
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class PreviewFilterResultUseCase(
    private val propertyRepositoryOld: PropertyRepository_Old,
    private val filterPropertiesUseCase: FilterPropertiesUseCase
) : UseCase<Request, Outcome> {

    @OptIn(FlowPreview::class)
    override fun invoke(request: Request): Flow<Outcome> {
        return propertyRepositoryOld.getActiveProperties().filterNotNull().flatMapConcat {
            filterPropertiesUseCase.invoke(
                FilterPropertiesUseCase.Request(
                    request.searchOption,
                    it
                )
            )
        }.map { Outcome(it.properties) }
    }

    data class Request(val searchOption: SearchOption)
    data class Outcome(val properties: List<Property>)

}
