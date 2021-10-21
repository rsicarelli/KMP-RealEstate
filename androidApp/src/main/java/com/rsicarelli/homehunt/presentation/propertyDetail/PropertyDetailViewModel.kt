package com.rsicarelli.homehunt.presentation.propertyDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt.core.model.ProgressBarState
import com.rsicarelli.homehunt.domain.usecase.GetSinglePropertyUseCase
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase
import com.rsicarelli.homehunt.domain.usecase.ToggleFavouriteUseCase
import com.rsicarelli.homehunt.ui.navigation.NavArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.rsicarelli.homehunt.domain.usecase.GetSinglePropertyUseCase.Request as GetSinglePropertyRequest
import com.rsicarelli.homehunt.domain.usecase.MarkAsViewedUseCase.Request as MarkAsViewedRequest
import com.rsicarelli.homehunt.domain.usecase.ToggleFavouriteUseCase.Request as ToggleFavouriteRequest


@HiltViewModel
class PropertyDetailViewModel @Inject constructor(
    private val getSingleProperty: GetSinglePropertyUseCase,
    private val toggleFavourite: ToggleFavouriteUseCase,
    private val markAsViewed: MarkAsViewedUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val state: MutableStateFlow<PropertyDetailState> =
        MutableStateFlow(PropertyDetailState())

    @OptIn(FlowPreview::class)
    fun init() = state
        .also { loadProperty() }
        .also { markAsViewed() }

    @OptIn(FlowPreview::class)
    private fun loadProperty() {
        getSingleProperty(GetSinglePropertyRequest(savedStateHandle.getReferenceId()))
            .onEach {
                state.value = state.value.copy(
                    property = it.property,
                    progressBarState = ProgressBarState.Idle
                )
            }
            .launchIn(viewModelScope)
    }

    private fun markAsViewed() {
        viewModelScope.launch {
            markAsViewed(MarkAsViewedRequest(savedStateHandle.getReferenceId())).single()
        }
    }

    fun onOpenVideoPreview() {
        state.value = state.value.copy(openVideoPreview = true)
    }

    fun onCloseVideoPreview() {
        state.value = state.value.copy(openVideoPreview = false)
    }

    fun onOpenGallery() {
        state.value = state.value.copy(openGallery = true)
    }

    fun onCloseGallery() {
        state.value = state.value.copy(openGallery = false)
    }

    fun onToggleFavourite(referenceId: String, isFavourited: Boolean) {
        viewModelScope.launch {
            toggleFavourite(ToggleFavouriteRequest(referenceId, isFavourited)).single()
        }
    }

    private fun SavedStateHandle.getReferenceId() =
        get<String>(NavArguments.PROPERTY_DETAIL) ?: error("ReferenceId cannot be null")
}



