package com.rsicarelli.homehunt.presentation.propertyDetail.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import coil.compose.LocalImageLoader
import coil.request.ImageRequest
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.rsicarelli.homehunt.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun GalleryBottomSheet(
    photosGalleryUrls: List<String>,
    isEnabled: Boolean,
    onCollapsed: () -> Unit
) {
    if (!isEnabled) return

    FullHeightBottomSheet(
        isEnabled = isEnabled,
        onCollapsed = onCollapsed
    ) {
        val imageLoader = LocalImageLoader.current
        val coroutinesScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()
        Box(contentAlignment = Alignment.BottomCenter) {
            HorizontalPager(
                count = photosGalleryUrls.size,
                state = pagerState
            ) { page ->
                var drawable: Drawable? by remember { mutableStateOf(null) }

                val request = ImageRequest.Builder(LocalContext.current)
                    .data(photosGalleryUrls[page])
                    .crossfade(true)
                    .build()

                LaunchedEffect(key1 = "imageLoader", block = {
                    coroutinesScope.launch {
                        drawable = imageLoader.execute(request).drawable
                    }
                })

                drawable?.let {
                    ZoomableImage(
                        drawable = it,
                        resetZoom = pagerState.currentPage != page
                    )
                }
            }
        }
    }
}