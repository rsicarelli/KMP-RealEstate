package com.rsicarelli.homehunt_kmm.data.cache.mappers

import com.rsicarelli.homehunt_kmm.GetPropertiesQuery
import com.rsicarelli.homehunt_kmm.domain.model.Location
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehuntkmm.datasource.cache._Property

internal fun _Property.toProperty(): Property {
    return Property(
        _id = this._id,
        price = price,
        title = title,
        location = location,
        surface = surface,
        dormCount = dormCount,
        bathCount = bathCount,
        avatarUrl = avatarUrl,
        tag = tag,
        propertyUrl = propertyUrl,
        videoUrl = videoUrl,
        fullDescription = fullDescription,
        locationDescription = locationDescription,
        characteristics = characteristics,
        photoGalleryUrls = photoGalleryUrls,
        pdfUrl = pdfUrl,
        origin = origin,
        isViewed = isViewed,
        isUpVoted = isFavourited,
        isActive = isActive
    )
}

internal fun List<_Property>.toPropertyList(): List<Property> {
    return this.map { it.toProperty() }
}


internal fun GetPropertiesQuery.Properties.toPropertyList(): List<Property> =
    this.result.map { it.toProperty() }

internal fun GetPropertiesQuery.Location.toLocation(): Location =
    Location(
        lat = lat,
        lng = lng,
        name = name,
        isApproximated = isApproximated,
        isUnknown = isUnknown
    )

internal fun GetPropertiesQuery.Result.toProperty(): Property =
    Property(
        _id = property._id,
        price = property.price,
        title = property.title,
        location = property.location.toLocation(),
        surface = property.surface,
        dormCount = property.dormCount,
        bathCount = property.dormCount,
        avatarUrl = property.avatarUrl,
        tag = property.tag,
        propertyUrl = property.propertyUrl,
        videoUrl = property.videoUrl,
        fullDescription = property.fullDescription,
        locationDescription = property.locationDescription,
        characteristics = property.characteristics,
        photoGalleryUrls = property.photoGalleryUrls,
        pdfUrl = property.pdfUrl,
        origin = property.origin,
        isViewed = isViewed,
        isUpVoted = isUpVoted,
        isActive = property.isActive
    )