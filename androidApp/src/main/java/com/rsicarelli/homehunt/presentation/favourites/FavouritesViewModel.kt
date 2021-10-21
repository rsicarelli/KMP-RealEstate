package com.rsicarelli.homehunt.presentation.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt.core.model.ProgressBarState
import com.rsicarelli.homehunt.domain.usecase.GetFavouritedPropertiesUseCase
import com.rsicarelli.homehunt.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavourites: GetFavouritedPropertiesUseCase,
    private val toggleFavourite: ToggleFavouriteUseCase,
) : ViewModel() {

    private val state: MutableStateFlow<FavouritesState> = MutableStateFlow(FavouritesState())

    fun init() = state.also { loadFavourites() }

    fun onToggleFavourite(referenceId: String, isFavourited: Boolean) {
        viewModelScope.launch {
            toggleFavourite(
                ToggleFavouriteUseCase.Request(
                    referenceId = referenceId,
                    isFavourited = isFavourited
                )
            ).single()
        }
    }

    private fun loadFavourites() = getFavourites(Unit)
        .onEach { outcome ->
            state.value = state.value.copy(
                properties = outcome.properties,
                progressBarState = ProgressBarState.Idle,
                isEmpty = outcome.properties.isEmpty()
            )
        }.launchIn(viewModelScope)

}