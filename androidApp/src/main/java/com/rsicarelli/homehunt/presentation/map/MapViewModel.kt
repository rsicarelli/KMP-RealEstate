package com.rsicarelli.homehunt.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.domain.usecase.GetFilteredPropertiesUseCase
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase
import com.rsicarelli.homehunt.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO the same as HomeViewModel, unify somehow
@HiltViewModel
class MapViewModel @Inject constructor(
    private val getFilteredPropertiesUseCase: GetFilteredPropertiesUseCase,
    private val toggleFavourite: ToggleFavouriteUseCase,
    private val markAsViewed: MarkAsViewedUseCase,
) : ViewModel() {

    private val state: MutableStateFlow<MapState> = MutableStateFlow(MapState())

    fun init() = state.also { loadProperties() }

    private fun loadProperties() {
        getFilteredPropertiesUseCase.invoke(Unit)
            .onEach { outcome ->
                val snippet = outcome.properties.filter {
                    state.value.propertySnippet.contains(it)
                }

                state.value = state.value.copy(
                    properties = outcome.properties,
                    progressBarState = ProgressBarState.Idle,
                    propertySnippet = snippet
                )
            }.launchIn(viewModelScope)
    }

    fun toggleFavourite(referenceId: String, isFavourited: Boolean) {
        viewModelScope.launch {
            toggleFavourite(
                request = ToggleFavouriteUseCase.Request(
                    referenceId,
                    isFavourited
                )
            ).single()
        }
    }

    fun onPropertyViewed(property: Property) {
        if (property.isViewed) return

        viewModelScope.launch {
            markAsViewed.invoke(MarkAsViewedUseCase.Request(property._id)).single()
        }
    }

    fun onMarkerSelected(property: Property) {
        onPropertyViewed(property)
        state.value = state.value.copy(
            propertySnippet = listOf(property),
            showSinglePreview = true,
            showClusteredPreview = false
        )
    }

    fun onClusterClicked(properties: List<Property>) {
        state.value = state.value.copy(
            propertySnippet = properties,
            showSinglePreview = false,
            showClusteredPreview = true
        )
    }

    fun onMapClicked() {
        viewModelScope.launch {
            state.value = state.value.copy(showSinglePreview = false, showClusteredPreview = false)
            delay(500) //delay to animate preview on UI
            state.value = state.value.copy(propertySnippet = emptyList())
        }
    }
}
