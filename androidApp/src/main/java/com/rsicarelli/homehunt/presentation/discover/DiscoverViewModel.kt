package com.rsicarelli.homehunt.presentation.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.repository.SearchOptionRepository
import com.rsicarelli.homehunt_kmm.domain.usecase.GetRecommendationsUseCase
import com.rsicarelli.homehunt_kmm.domain.usecase.GetSearchOptionSettings
import com.rsicarelli.homehunt_kmm.domain.usecase.MarkAsViewedUseCase
import com.rsicarelli.homehunt_kmm.domain.usecase.ToggleFavouriteUseCase
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SettingsListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.rsicarelli.homehunt_kmm.domain.usecase.MarkAsViewedUseCase.Request as MarkAsViewedRequest

@OptIn(ExperimentalSettingsApi::class)
@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getRecommendations: GetRecommendationsUseCase,
    private val searchOptionRepository: SearchOptionRepository,
    private val toggleFavourite: ToggleFavouriteUseCase,
    private val markAsViewed: MarkAsViewedUseCase,
) : ViewModel() {

    private var listen: SettingsListener? = null

    private val state: MutableStateFlow<DiscoverState> =
        MutableStateFlow(DiscoverState())

    init {
        viewModelScope.launch {
            listen = searchOptionRepository.listen {
                loadProperties()
            }
        }
    }

    override fun onCleared() {
        listen?.deactivate()
        super.onCleared()
    }

    fun init() = state.also { loadProperties() }

    fun loadProperties() {
        viewModelScope.launch {
            getRecommendations.invoke(Unit)
                .collectLatest {
                    state.value = state.value.copy(
                        properties = it.properties,
                        progressBarState = ProgressBarState.Idle,
                        isEmpty = it.properties.isEmpty()
                    )
                }
        }
    }

    fun onUpVote(referenceId: String) {
        toggleRatings(referenceId = referenceId, isUpVoted = true)
    }

    fun onDownVote(referenceId: String) {
        toggleRatings(referenceId = referenceId, isUpVoted = false)
    }

    private fun toggleRatings(referenceId: String, isUpVoted: Boolean) {
        viewModelScope.launch {
            state.value = state.value.copy(itemRemoved = referenceId)

            delay(400)

            state.value = state.value.copy(
                itemRemoved = null,
                properties = state.value.properties.toMutableList()
                    .filterNot { it._id == referenceId },
            )

            toggleFavourite(
                request = ToggleFavouriteUseCase.Request(
                    referenceId,
                    isUpVoted
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