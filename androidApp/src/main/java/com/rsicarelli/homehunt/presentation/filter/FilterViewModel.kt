package com.rsicarelli.homehunt.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt.core.model.UiEvent
import com.rsicarelli.homehunt.domain.usecase.GetFilterPreferencesUseCase
import com.rsicarelli.homehunt.domain.usecase.PreviewFilterResultUseCase
import com.rsicarelli.homehunt.domain.usecase.SaveFilterPreferencesUseCase
import com.rsicarelli.homehunt.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val previewFilterResult: PreviewFilterResultUseCase,
    private val getFilter: GetFilterPreferencesUseCase,
    private val saveFilter: SaveFilterPreferencesUseCase
) : ViewModel() {

    private val state: MutableStateFlow<FilterState> = MutableStateFlow(FilterState())

    @OptIn(FlowPreview::class)
    fun init(): Flow<FilterState> = getFilter.invoke(Unit)
        .onEach { state.value = it.searchOption.toState() }
        .flatMapConcat { state }
        .onEach { previewResults() }

    fun onAvailabilitySelectionChanged(newValue: Boolean) {
        state.value = state.value.copy(availableOnly = newValue)
    }

    fun onLongTermRentalSelectionChanged(newValue: Boolean) {
        state.value = state.value.copy(longTermOnly = newValue)
    }

    fun onVisibilitySelectionChanged(newValue: Boolean) {
        state.value = state.value.copy(showSeen = newValue)
    }

    fun onBathSelectionChanged(newValue: Int) {
        state.value = state.value.copy(bathCount = newValue)
    }

    fun onSurfaceRangeChanged(newRange: ClosedFloatingPointRange<Float>) {
        state.value = state.value.copy(surfaceRange = newRange)
    }

    fun onDormsSelectionChanged(newValue: Int) {
        state.value = state.value.copy(dormCount = newValue)
    }

    fun onPriceRangeChanged(newRange: ClosedFloatingPointRange<Float>) {
        state.value = state.value.copy(priceRange = newRange)
    }

    private fun previewResults() {
        viewModelScope.launch {
            delay(1000)
            previewFilterResult(
                request = PreviewFilterResultUseCase.Request(state.value.toSearchOption())
            ).first().run {
                state.value = state.value.copy(previewResultCount = properties.size)
            }
        }
    }

    fun onSaveFilter() {
        viewModelScope.launch {
            saveFilter(request = SaveFilterPreferencesUseCase.Request(state.value.toSearchOption()))
                .collect {
                    state.value = state.value.copy(uiEvent = UiEvent.Navigate(Screen.Home.route))
                }
        }
    }

}