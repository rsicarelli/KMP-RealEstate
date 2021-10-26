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
        isUpVoted = isUpVoted,
        isDownVoted = isDownVoted,
        isActive = isActive
    )
}

internal fun List<_Property>.toPropertyList(): List<Property> {
    return this.map { it.toProperty() }
}


internal fun GetPropertiesQuery.Properties.toPropertyList(): List<Property> =
    this.results.map { it.toProperty() }

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
        _id = _id,
        price = price,
        title = title,
        location = location.toLocation(),
        surface = surface,
        dormCount = dormCount,
        bathCount = dormCount,
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
        isActive = isActive
    )