package com.rsicarelli.homehunt.presentation.recommendations.components

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.util.toCurrency
import com.rsicarelli.homehunt.presentation.components.IconText
import com.rsicarelli.homehunt.presentation.propertyDetail.components.StaticMapView
import com.rsicarelli.homehunt.ui.theme.*
import com.rsicarelli.homehunt_kmm.domain.model.Location
import com.rsicarelli.homehunt_kmm.domain.model.Property
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PropertyPager(
    properties: List<Property>,
    onNavigate: (id: String) -> Unit,
    onUpVote: (id: String) -> Unit,
    onDownVote: (id: String) -> Unit,
    itemRemoved: String?,
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
                val (mainPhoto, propertyDetails, photoGallery, map) = createRefs()

                MainPicture(
                    modifier = Modifier.constrainAs(mainPhoto) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                    image = property.photoGalleryUrls.first(),
                    onClick = { onNavigate(property._id) }
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
fun PropertyMap(
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
                modifier = Modifier.clickable { },
                isLiteMode = true,
                showRadius = false
            )
        }
    }
}

@Composable
fun PropertyGallery(
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
fun PropertyInfo(
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
fun MainPicture(
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