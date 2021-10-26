package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.presentation.components.GalleryCarousel

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalPagerApi::class
)
@Composable
fun PropertyDetail(
    property: Property,
    onOpenGallery: () -> Unit,
    onPlayVideo: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            GalleryCarousel(
                photoGallery = property.photoGalleryUrls,
                onOpenGallery = onOpenGallery,
                footer = { currentPage ->
                    PropertyGalleryCarouselFooter(
                        hasVideo = property.videoUrl != null && property.videoUrl?.isNotEmpty() ?: false,
                        currentPage = currentPage,
                        onPlayVideo = onPlayVideo,
                        gallerySize = property.photoGalleryUrls.size
                    )
                }
            )
        }
        item {
            PropertyHeader(property = property)
        }
        item {
            PropertyFeatures(characteristics = property.characteristics)
        }
        item {
            PropertyMap(
                location = property.location,
                onMapClick = {
                    //TODO
                }
            )
        }
        item {
            PropertyContentDescription(
                titleRes = R.string.about_this_property,
                content = property.fullDescription
            )
        }
        item {
            PropertyContentDescription(
                titleRes = R.string.location_description,
                content = property.locationDescription
            )
        }
        item {
            PropertyFooter(
                reference = property._id,
                propertyUrl = property.propertyUrl,
                pdfUrl = property.pdfUrl
            )
        }
    }
}