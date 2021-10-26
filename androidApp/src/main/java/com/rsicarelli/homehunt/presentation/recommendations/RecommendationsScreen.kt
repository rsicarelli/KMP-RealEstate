package com.rsicarelli.homehunt.presentation.recommendations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.util.toCurrency
import com.rsicarelli.homehunt.presentation.components.CircularIndeterminateProgressBar
import com.rsicarelli.homehunt.presentation.components.EmptyContent
import com.rsicarelli.homehunt.presentation.components.IconText
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.propertyDetail.components.StaticMapView
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.state.AppState
import com.rsicarelli.homehunt.ui.theme.*
import com.rsicarelli.homehunt_kmm.core.model.ProgressBarState
import com.rsicarelli.homehunt_kmm.domain.model.Location
import com.rsicarelli.homehunt_kmm.domain.model.Property
import utils.Fixtures
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendationsScreen(
    appState: AppState,
    viewModel: RecommendationsViewModel = hiltViewModel()
) {

    val stateFlowLifecycleAware = viewModel.rememberOnLifecycle {
        viewModel.init().flowWithLifecycle(
            lifecycle = it.lifecycle,
            minActiveState = Lifecycle.State.RESUMED
        )
    }

    val state by stateFlowLifecycleAware.collectAsState(initial = RecommendationsState())

    val actions = RecommendationsActions(
        onNavigate = appState::navigate,
        onDownVote = viewModel::onDownVote,
        onUpVote = viewModel::onUpVote,
        onPropertyViewed = viewModel::onPropertyViewed,
    )

    RecommendationsContent(
        state = state,
        actions = actions
    )
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
private fun RecommendationsContent(state: RecommendationsState, actions: RecommendationsActions) {

    when {
        state.properties.isNotEmpty() -> PropertyPager(state.properties, actions)
        state.progressBarState != ProgressBarState.Loading -> EmptyContent()
        else -> CircularIndeterminateProgressBar(progressBarState = state.progressBarState)
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun PropertyPager(
    properties: List<Property>,
    actions: RecommendationsActions,
) {
    val pagerState = rememberPagerState()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (header, pager, bottomBar) = createRefs()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(header) {
                    top.linkTo(parent.top, Size_Regular)
                    start.linkTo(parent.start, Size_Large)
                },
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1.0f),
                text = "${properties.size} ${stringResource(id = R.string.properties)}",
                style = MaterialTheme.typography.h6
            )
        }

        HorizontalPager(
            modifier = Modifier
                .constrainAs(pager) {
                    top.linkTo(header.bottom, Size_Large)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(510.dp),
            state = pagerState,
            count = properties.size,
            contentPadding = PaddingValues(horizontal = 24.dp),
        ) { page ->
            PropertySnapshot(page, properties[page], actions)
        }

        val property = properties[pagerState.currentPage]

        BottomBar(
            modifier = Modifier.constrainAs(bottomBar) {
                bottom.linkTo(parent.bottom, Size_Large)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            isUpVoted = property.isUpVoted,
            onDownVoted = { actions.onDownVote(property._id) },
            onUpVoted = { actions.onUpVote(property._id) }
        )

    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
private fun PagerScope.PropertySnapshot(
    page: Int,
    property: Property,
    actions: RecommendationsActions
) {

    AnimatedVisibility(
        visible = !property.isDownVoted,
        exit = fadeOut(
            animationSpec = TweenSpec(200, 200, FastOutLinearInEasing)
        )
    ) {
        Card(
            Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                    lerp(
                        start = 0.90f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.background
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (mainPhoto, propertyDetails, photoGallery, map) = createRefs()

                MainPicture(
                    modifier = Modifier.constrainAs(mainPhoto) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                    image = property.photoGalleryUrls.first(),
                    onClick = {
                        actions.onNavigate("${Screen.PropertyDetail.route}/${property._id}")
                    }
                )

                PropertyInfo(
                    property = property,
                    modifier = Modifier.constrainAs(propertyDetails) {
                        bottom.linkTo(mainPhoto.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                )

                PropertyGallery(
                    photoGallery = property.photoGalleryUrls,
                    modifier = Modifier.constrainAs(photoGallery) {
                        top.linkTo(mainPhoto.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                )

                PropertyMap(
                    location = property.location,
                    modifier = Modifier.constrainAs(map) {
                        top.linkTo(photoGallery.bottom, Size_Small)
                        end.linkTo(parent.end, Size_Small)
                        start.linkTo(parent.start, Size_Small)
                        bottom.linkTo(parent.bottom, Size_Regular)
                    },
                )
            }
        }
    }
}

@Composable
private fun PropertyMap(
    modifier: Modifier,
    location: Location
) {
    Box(modifier = modifier.padding(8.dp)) {
        Surface(
            modifier = Modifier
                .height(130.dp)
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
            StaticMapView(
                location = location,
                modifier = Modifier
                    .clickable { },
                isLiteMode = true
            )
        }
    }
}

@Composable
private fun PropertyGallery(
    modifier: Modifier,
    photoGallery: List<String>
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(photoGallery) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .width(140.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = rememberImagePainter(
                        data = it,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
private fun PropertyInfo(
    modifier: Modifier = Modifier,
    property: Property,
) {
    Box(
        modifier = modifier.clip(
            RoundedCornerShape(
                bottomEnd = Size_Medium,
                bottomStart = Size_Medium
            )
        )
    ) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0f),
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Black.copy(alpha = 0.6f),
                            Color.Black.copy(alpha = 0.7f),
                        )
                    )
                )
                .padding(top = 4.dp, start = 8.dp, end = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconText(
                    text = "${property.dormCount}",
                    leadingIcon = R.drawable.ic_round_double_bed
                )
                Spacer(modifier = Modifier.width(Size_Small))
                IconText(
                    text = "${property.bathCount}",
                    leadingIcon = R.drawable.ic_round_shower
                )
                Spacer(modifier = Modifier.width(Size_Small))
                IconText(
                    modifier = Modifier.weight(1f),
                    text = "${property.surface} m²",
                    leadingIcon = R.drawable.ic_round_ruler
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = "${property.price.toCurrency()}",
                    style = MaterialTheme.typography.h4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}


@Composable
private fun MainPicture(
    modifier: Modifier = Modifier,
    image: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clip(
            RoundedCornerShape(
                bottomEnd = Size_Medium,
                bottomStart = Size_Medium
            )
        )
    ) {
        Image(
            modifier = modifier
                .height(256.dp)
                .fillMaxWidth()
                .clickable { onClick() },
            painter = rememberImagePainter(
                data = image,
                builder = { crossfade(true) }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }

}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    isUpVoted: Boolean,
    onDownVoted: () -> Unit,
    onUpVoted: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onDownVoted,
            modifier = Modifier
                .then(Modifier.size(60.dp))
                .border(
                    BorderSizeSmallest,
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f),
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_round_close),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                tint = Color.Red,
                contentDescription = "next",
            )
        }

        Spacer(modifier = Modifier.width(48.dp))
        IconButton(
            onClick = onUpVoted,
            modifier = Modifier
                .then(Modifier.size(60.dp))
                .border(
                    BorderSizeSmallest,
                    color = MaterialTheme.colors.primary.copy(alpha = 0.3f),
                    shape = CircleShape
                ),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_round_favorite),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                tint = Green_300,
                contentDescription = "next",
            )
        }
    }
}

@Preview
@Composable
private fun RecommendationPreview() {
    HomeHuntTheme(isPreview = true) {
        RecommendationsContent(
            state = RecommendationsState(properties = Fixtures.aListOfProperty),
            actions = RecommendationsActions(
                onDownVote = {},
                onUpVote = {},
                onNavigate = {},
                onPropertyViewed = {}
            )
        )
    }
}