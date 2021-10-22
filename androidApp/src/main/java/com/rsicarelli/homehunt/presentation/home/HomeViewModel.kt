package com.rsicarelli.homehunt.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.usecase.GetFilteredPropertiesUseCase
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase
import com.rsicarelli.homehunt.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase.Request as MarkAsViewedRequest

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
                    properties = it.properties,
                    progressBarState = ProgressBarState.Idle,
                    isEmpty = it.properties.isEmpty()
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
            markAsViewed.invoke(MarkAsViewedRequest(property._id)).single()
        }
    }

}