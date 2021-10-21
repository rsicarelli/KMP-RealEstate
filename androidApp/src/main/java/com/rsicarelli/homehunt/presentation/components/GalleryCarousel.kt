package com.rsicarelli.homehunt.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Medium

@OptIn(ExperimentalPagerApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
fun GalleryCarousel(
    state: PagerState = rememberPagerState(),
    photoGallery: List<String>,
    onOpenGallery: () -> Unit,
    aspectRatio: Float = 1.64f,
    onSecondPage: () -> Unit = { },
    footer: @Composable BoxScope.(Int) -> Unit = {}
) {
    var nextPageNotified by remember { mutableStateOf(false) }

    Box(modifier = Modifier.aspectRatio(aspectRatio), contentAlignment = Alignment.BottomEnd) {
        HorizontalPager(count = photoGallery.size, state = state) { page ->
            if (page == 2 && !nextPageNotified) {
                onSecondPage()
                nextPageNotified = true
            }

            Box(
                modifier = Modifier.clickable { onOpenGallery() }
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = rememberImagePainter(
                        data = photoGallery[page],
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
        footer(state.currentPage)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
private fun GalleryCarouselPreview() {
    HomeHuntTheme(isPreview = true) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        {
            val pagerState = rememberPagerState()
            GalleryCarousel(
                state = pagerState,
                photoGallery = listOf("a", "b", "d"),
                onOpenGallery = { },
                onSecondPage = {}
            ) {
                Tag(text = "A footer", modifier = Modifier.padding(Size_Medium))
            }
        }
    }
}
