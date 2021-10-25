package com.rsicarelli.homehunt.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.usecase.GetFilteredPropertiesUseCase
import com.rsicarelli.homehunt_kmm.domain.usecase.MarkAsViewedUseCase
import com.rsicarelli.homehunt_kmm.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.rsicarelli.homehunt_kmm.domain.usecase.MarkAsViewedUseCase.Request as MarkAsViewedRequest

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFilteredPropertiesUseCase: GetFilteredPropertiesUseCase,
    private val toggleFavourite: ToggleFavouriteUseCase,
    private val markAsViewed: MarkAsViewedUseCase,
) : ViewModel() {

    private val state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())

    fun init() = state.also { loadProperties() }

    private fun loadProperties() {
        getFilteredPropertiesUseCase.invoke(Unit)
            .onEach {
                state.value = state.value.copy(
                    properties = it.properties.filterNot { it.isDownVoted },
                    progressBarState = ProgressBarState.Idle,
                    isEmpty = it.properties.isEmpty()
                )
            }.launchIn(viewModelScope)
    }

    fun onUpVote(referenceId: String) {
        viewModelScope.launch {
            val newProperties = state.value.properties.toMutableList()
            val index = newProperties.indexOfFirst { it._id == referenceId }

            if (newProperties[index].isUpVoted) {
                newProperties[index] = newProperties[index].copy(isUpVoted = false)
            } else {
                newProperties[index] = newProperties[index].copy(isUpVoted = true)
            }

            state.value = state.value.copy(
                properties = newProperties,
            )

            toggleFavourite(
                request = ToggleFavouriteUseCase.Request(
                    referenceId,
                    true
                )
            ).single()
        }
    }

    fun onDownVote(referenceId: String) {
        viewModelScope.launch {
            val newProperties = state.value.properties.toMutableList()
            val index = newProperties.indexOfFirst { it._id == referenceId }

            newProperties[index] = newProperties[index].copy(isDownVoted = true)

            state.value = state.value.copy(properties = newProperties)

            toggleFavourite(
                request = ToggleFavouriteUseCase.Request(
                    referenceId,
                    false
                )
            ).single()

            delay(120)

            state.value = state.value.copy(
                properties = newProperties.filterNot { it._id == referenceId },
            )
        }
    }

    fun onPropertyViewed(property: Property) {
        if (property.isViewed) return

        viewModelScope.launch {
            markAsViewed.invoke(MarkAsViewedRequest(property._id)).single()
        }
    }

}