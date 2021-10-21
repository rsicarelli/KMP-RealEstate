package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.theme.Size_X_Small
import com.rsicarelli.homehunt.ui.theme.Size_Small
import com.rsicarelli.homehunt.ui.theme.Size_Regular
import com.rsicarelli.homehunt.ui.theme.Size_X_Large

@ExperimentalAnimationApi
@Composable
fun PropertyGalleryCarouselFooter(
    hasVideo: Boolean,
    currentPage: Int,
    onPlayVideo: () -> Unit,
    gallerySize: Int
) {
    Row(
        modifier = Modifier
            .padding(Size_Small)
    ) {
        AnimatedVisibility(
            visible = currentPage == 0 && hasVideo,
            enter = fadeIn(
                initialAlpha = 0.4f
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 250)
            )
        ) {
            Row(
                modifier = Modifier
                    .height(Size_X_Large)
                    .background(
                        color = MaterialTheme.colors.background.copy(alpha = 0.8f),
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(
                        start = Size_Small,
                        end = Size_Regular
                    )
                    .clickable { onPlayVideo() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_play),
                    contentDescription = stringResource(id = R.string.video_available)
                )
                Spacer(Modifier.width(Size_X_Small))
                Text(
                    text = stringResource(id = R.string.video_available),
                    style = MaterialTheme.typography.button,
                )
            }
        }
        Spacer(modifier = Modifier.width(Size_Small))
        PagerIndicator(currentPage = currentPage, totalItems = gallerySize)
    }
}