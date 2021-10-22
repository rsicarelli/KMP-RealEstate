package com.rsicarelli.homehunt.presentation.propertyDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.rsicarelli.homehunt.ui.state.HomeHuntState
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.propertyDetail.components.GalleryBottomSheet
import com.rsicarelli.homehunt.presentation.propertyDetail.components.PropertyDetail
import com.rsicarelli.homehunt.presentation.propertyDetail.components.PropertyTopBar
import com.rsicarelli.homehunt.presentation.propertyDetail.components.PropertyVideoBottomSheet

@Composable
fun PropertyDetailScreen(
    homeHuntState: HomeHuntState
) {
    val viewModel: PropertyDetailViewModel = hiltViewModel()
    val stateFlowLifecycleAware = viewModel.rememberOnLifecycle {
        viewModel.init().flowWithLifecycle(
            lifecycle = it.lifecycle,
            minActiveState = Lifecycle.State.STARTED
        )
    }

    val state by stateFlowLifecycleAware.collectAsState(PropertyDetailState())

    val actions = PropertyDetailActions(
        onOpenVideoPreview = viewModel::onOpenVideoPreview,
        onCloseVideoPreview = viewModel::onCloseVideoPreview,
        onOpenGallery = viewModel::onOpenGallery,
        onCloseGallery = viewModel::onCloseGallery,
        onToggleFavourite = viewModel::onToggleFavourite,
        onNavigateUp = homeHuntState::navigateUp
    )

    PropertyDetailContent(
        state = state,
        actions = actions,
    )
}

@Composable
private fun PropertyDetailContent(
    state: PropertyDetailState,
    actions: PropertyDetailActions,
) {
    state.property?.let { property ->
        Box(modifier = Modifier.fillMaxSize()) {
            PropertyDetail(
                property = property,
                onOpenGallery = actions.onOpenGallery,
                onPlayVideo = actions.onOpenVideoPreview
            )
            PropertyTopBar(
                isFavourited = property.isUpVoted,
                onBackClicked = actions.onNavigateUp,
                onFavouriteClick = {
                    actions.onToggleFavourite(
                        property._id,
                        !property.isUpVoted
                    )
                }
            )
            GalleryBottomSheet(
                photosGalleryUrls = property.photoGalleryUrls,
                isEnabled = state.openGallery,
                onCollapsed = actions.onCloseGallery
            )
            PropertyVideoBottomSheet(
                videoUrl = property.videoUrl,
                isEnabled = state.openVideoPreview,
                onCollapsed = actions.onCloseVideoPreview
            )
        }
    }
}

