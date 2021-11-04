package com.rsicarelli.homehunt.presentation.discover.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import com.rsicarelli.homehunt.BuildConfig
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.util.toCurrency
import com.rsicarelli.homehunt.presentation.components.IconText
import com.rsicarelli.homehunt.ui.theme.*
import com.rsicarelli.homehunt_kmm.domain.model.Location
import com.rsicarelli.homehunt_kmm.domain.model.Property
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PropertyPager(
    properties: List<Property>,
    onPropertyViewed: (Property) -> Unit,
    onNavigate: (id: String) -> Unit,
    onUpVote: (id: String) -> Unit,
    onDownVote: (id: String) -> Unit,
    itemRemoved: String?,
) {
    val pagerState = rememberPagerState()

    onPropertyViewed(properties[pagerState.currentPage])

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
                    bottom.linkTo(bottomBar.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(510.dp),
            state = pagerState,
            count = properties.size,
            contentPadding = PaddingValues(horizontal = 24.dp),
        ) { page ->
            PropertySnapshot(page, properties[page], onNavigate, itemRemoved)
        }

        val property = properties[pagerState.currentPage]

        BottomBar(
            modifier = Modifier.constrainAs(bottomBar) {
                top.linkTo(pager.bottom)
                bottom.linkTo(parent.bottom, Size_Small)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onDownVoted = { onDownVote(property._id) },
            onUpVoted = { onUpVote(property._id) }
        )
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun PagerScope.PropertySnapshot(
    page: Int,
    property: Property,
    onNavigate: (String) -> Unit,
    itemRemoved: String?
) {
    val isVisible = itemRemoved?.let {
        it != property._id
    } ?: true

    AnimatedVisibility(
        visible = isVisible,
        exit = fadeOut(),
        enter = fadeIn()
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
            backgroundColor = Light_Grey
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
                val (mainPhoto, propertyDetails, map) = createRefs()

                MainPicture(
                    modifier = Modifier.constrainAs(mainPhoto) {
                        top.linkTo(parent.top, 8.dp)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                    images = property.photoGalleryUrls,
                    onClick = { onNavigate(property._id) }
                )

                PropertyInfo(
                    property = property,
                    modifier = Modifier.constrainAs(propertyDetails) {
                        top.linkTo(map.bottom)
                        bottom.linkTo(parent.bottom, 8.dp)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                )

                PropertyMap(
                    location = property.location,
                    modifier = Modifier.constrainAs(map) {
                        top.linkTo(mainPhoto.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
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
    Box(
        modifier = modifier
            .height(140.dp)
            .padding(8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(
                data = location.toStaticMap(BuildConfig.GOOGLE_MAPS_API_KEY),
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PropertyInfo(
    modifier: Modifier = Modifier,
    property: Property,
) {
    Column(
        modifier.padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "${property.price.toCurrency()}",
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

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
                text = "${property.surface} m²",
                leadingIcon = R.drawable.ic_round_ruler
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPicture(
    modifier: Modifier = Modifier,
    images: List<String>,
    onClick: () -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(images) { index, url ->
            val startPadding = if (index == 0) 8.dp else 0.dp

            Card(
                modifier = Modifier
                    .height(240.dp)
                    .width(320.dp)
                    .padding(start = startPadding, end = 8.dp),
                elevation = 8.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                Image(
                    modifier = modifier
                        .fillMaxSize()
                        .clickable { onClick() },
                    painter = rememberImagePainter(
                        data = url,
                        builder = { crossfade(true) }
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}