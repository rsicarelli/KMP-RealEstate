package com.rsicarelli.homehunt.presentation.propertyDetail.components

import android.content.res.Configuration
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PropertyVideoBottomSheet(
    videoUrl: String?,
    isEnabled: Boolean,
    onCollapsed: () -> Unit
) {
    if (!isEnabled || videoUrl == null) return

    val configuration = LocalConfiguration.current

    FullHeightBottomSheet(
        isEnabled = isEnabled,
        onCollapsed = onCollapsed
    ) {
        val pageUrl = convertVideoUrl(
            videoUrl = videoUrl,
            isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        )

        WebContent(pageUrl = pageUrl)
    }
}

private fun convertVideoUrl(videoUrl: String, isLandscape: Boolean): String {
    val sizes: Pair<Int, Int> = if (isLandscape) {
        Pair(800, 300)
    } else {
        Pair(380, 300)
    }

    val (width, height) = sizes

    return "<html><body leftmargin=\"0\" topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\"><iframe src=\"${videoUrl}\" width=\"${width}\" height=\"${height}\" frameborder=\"0\" allowfullscreen=true></iframe></body></html>"
}