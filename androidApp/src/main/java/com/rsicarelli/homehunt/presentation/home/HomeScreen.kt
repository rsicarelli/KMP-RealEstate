package com.rsicarelli.homehunt.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.*
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.ui.state.HomeHuntState
import com.rsicarelli.homehunt.core.util.toCurrency
import com.rsicarelli.homehunt.presentation.components.CircularIndeterminateProgressBar
import com.rsicarelli.homehunt.presentation.components.EmptyContent
import com.rsicarelli.homehunt.presentation.components.IconText
import com.rsicarelli.homehunt.presentation.components.rememberOnLifecycle
import com.rsicarelli.homehunt.presentation.propertyDetail.components.PagerIndicator
import com.rsicarelli.homehunt.presentation.propertyDetail.components.StaticMapView
import com.rsicarelli.homehunt.ui.navigation.Screen
import com.rsicarelli.homehunt.ui.theme.*
import com.rsicarelli.homehunt_kmm.domain.model.Property
import utils.Fixtures
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeHuntState: HomeHuntState,
    viewModel: HomeViewModel = hiltViewModel()

) {
    val stateFlowLifecycleAware = viewModel.rememberOnLifecycle {
        viewModel.init().flowWithLifecycle(
            lifecycle = it.lifecycle,
            minActiveState = Lifecycle.State.RESUMED
        )
    }

    val state by stateFlowLifecycleAware.collectAsState(initial = HomeState())

    val actions = HomeActions(
        onNavigate = homeHuntState::navigate,
        onDownVote = viewModel::onDownVote,
        onUpVote = viewModel::onUpVote,
        onPropertyViewed = viewModel::onPropertyViewed,
    )

    HomeContent(
        state = state,
        actions = actions,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun HomeContent(
    state: HomeState,
    actions: HomeActions,
) {
    if (state.properties.isNotEmpty()) {
        PropertyPager(state, actions)
    } else if (state.isEmpty) {
        EmptyContent()
    }

    CircularIndeterminateProgressBar(state.progressBarState)

}

@OptIn(ExperimentalPagerApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
private fun PropertyPager(state: HomeState, actions: HomeActions) {
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .statusBarsPadding()
                .padding(Size_Large),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1.0f),
                text = "${state.properties.size} ${stringResource(id = R.string.results)}",
                style = MaterialTheme.typography.h4
            )

            IconButton(
                onClick = { actions.onNavigate(Screen.Filter.route) }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.ic_round_filter),
                    contentDescription = stringResource(id = R.string.filter)
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            count = state.properties.size,
            contentPadding = PaddingValues(horizontal = 24.dp),
        ) { page ->
            PropertySnapshot(page, state.properties[page], actions)
        }
    }
}

@OptIn(ExperimentalPagerApi::class, androidx.compose.animation.ExperimentalAnimationApi::class)
@Composable
private fun PagerScope.PropertySnapshot(
    page: Int,
    property: Property,
    actions: HomeActions
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
                .fillMaxWidth()
        ) {
            Column {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                ) {
                    val (pagerIndicator, photoGallery, propertyDetails) = createRefs()
                    val pagerState = rememberPagerState()
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.constrainAs(photoGallery) {
                            bottom.linkTo(parent.bottom)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        },
                        count = property.photoGalleryUrls.size
                    ) { page ->
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { },
                            painter = rememberImagePainter(
                                data = property.photoGalleryUrls[page],
                                builder = {
                                    crossfade(true)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }

                    PagerIndicator(
                        modifier = Modifier.constrainAs(pagerIndicator) {
                            bottom.linkTo(propertyDetails.top)
                            end.linkTo(parent.end, 8.dp)
                        },
                        currentPage = pagerState.currentPage,
                        totalItems = property.photoGalleryUrls.size
                    )

                    Column(modifier = Modifier
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
                        .constrainAs(propertyDetails) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                        }
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

                Column(Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp)) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(shape = MaterialTheme.shapes.medium)
                    ) {
                        StaticMapView(
                            property = property,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { },
                            lat = property.location.lat,
                            lng = property.location.lng,
                            drawRadius = property.location.isApproximated,
                            isLiteMode = true
                        )
                    }
                    BottomBar(
                        isUpVoted = property.isUpVoted,
                        onDownVoted = { actions.onDownVote(property._id) },
                        onUpVoted = { actions.onUpVote(property._id) }
                    )
                }
            }
        }
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
                .then(Modifier.size(52.dp))
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
                contentDescription = "next",
            )
        }

        Spacer(modifier = Modifier.width(48.dp))
        IconButton(
            onClick = onUpVoted,
            modifier = Modifier
                .then(Modifier.size(52.dp))
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
                tint = if (isUpVoted) Green_300 else MaterialTheme.colors.primary,
                contentDescription = "next",
            )
        }
    }
}

@Composable
@Preview()
private fun HomeScreenPreview() {
    HomeHuntTheme(isPreview = true) {
        HomeContent(
            actions = HomeActions({ }, { }, { }, { }),
            state = HomeState(properties = Fixtures.aListOfProperty),
        )
    }
}


