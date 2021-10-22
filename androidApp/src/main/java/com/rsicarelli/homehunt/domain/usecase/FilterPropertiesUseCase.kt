package com.rsicarelli.homehunt.domain.usecase

import com.rsicarelli.homehunt_kmm.core.model.UseCase
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.domain.model.SearchOption
import com.rsicarelli.homehunt.domain.usecase.FilterPropertiesUseCase.Outcome
import com.rsicarelli.homehunt.domain.usecase.FilterPropertiesUseCase.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FilterPropertiesUseCase(
) : UseCase<Request, Outcome> {

    override fun invoke(request: Request): Flow<Outcome> {
        return flow {
            val (searchOption, properties) = request

            emit(Outcome(searchOption.applyFilter(properties)))
        }
    }

    data class Request(
        val searchOption: SearchOption,
        val properties: List<Property>
    )

    data class Outcome(val properties: List<Property>)

}
