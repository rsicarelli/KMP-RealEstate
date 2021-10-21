package com.rsicarelli.homehunt.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.util.toCurrency
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt_kmm.domain.model.toTag
import com.rsicarelli.homehunt.presentation.components.FavouritableIconButton
import com.rsicarelli.homehunt.presentation.components.GalleryCarousel
import com.rsicarelli.homehunt.presentation.components.IconText
import com.rsicarelli.homehunt.presentation.components.ListingTag
import com.rsicarelli.homehunt.ui.theme.*
import utils.Fixtures

@OptIn(
    ExperimentalCoilApi::class, ExperimentalPagerApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun PropertyListItem(
    property: Property,
    onSelectProperty: (Property) -> Unit,
    onFavouriteClick: () -> Unit,
    onViewedGallery: () -> Unit,
    gallerySize: Dp = GalleryItemSize
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Size_Medium,
                bottom = Size_Small
            )
            .clickable {
                onSelectProperty(property)
            },
        elevation = ElevationSize
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                val pagerState = rememberPagerState()
                GalleryCarousel(
                    state = pagerState,
                    photoGallery = property.photoGalleryUrls,
                    onOpenGallery = { onSelectProperty(property) },
                    onSecondPage = onViewedGallery,
                )
                Row(
                    modifier = Modifier.padding(top = Size_Small, end = Size_Small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ListingTag(
                        isPropertyActive = property.isActive,
                        propertyTag = property.tag.toTag()
                    )
                    FavouritableIconButton(
                        onClick = onFavouriteClick,
                        isFavourited = property.isFavourited
                    )
                }
            }
            Spacer(modifier = Modifier.height(Size_Small))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Size_Regular, end = Size_Regular),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${property.price.toCurrency()}",
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(Size_Small))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(top = Size_X_Small, start = Size_X_Small),
                    text = property.location.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.primaryVariant
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = Size_Regular,
                        start = Size_Regular,
                        end = Size_Regular,
                        top = Size_X_Small
                    )
            ) {
                IconText(
                    text = "${property.dormCount}",
                    leadingIcon = R.drawable.ic_round_double_bed
                )
                Spacer(modifier = Modifier.width(Size_Regular))
                IconText(
                    text = "${property.bathCount}",
                    leadingIcon = R.drawable.ic_round_shower
                )
                Spacer(modifier = Modifier.width(Size_Regular))
                IconText(
                    modifier = Modifier.weight(1F),
                    text = "${property.surface} m²",
                    leadingIcon = R.drawable.ic_round_ruler
                )
                AnimatedVisibility(
                    visible = property.viewedByMe(),
                    enter = fadeIn(initialAlpha = 0.4f),
                    exit = fadeOut(animationSpec = tween(durationMillis = 250))
                ) {
                    IconText(
                        text = stringResource(id = R.string.viewed),
                        leadingIcon = R.drawable.ic_round_visibility
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun PropertyListItemPreview() {
    HomeHuntTheme(isPreview = true) {
        LazyColumn() {
            item {
                PropertyListItem(
                    property = Fixtures.aProperty,
                    onSelectProperty = { },
                    onFavouriteClick = { },
                    onViewedGallery = { }
                )
            }
        }
    }
}