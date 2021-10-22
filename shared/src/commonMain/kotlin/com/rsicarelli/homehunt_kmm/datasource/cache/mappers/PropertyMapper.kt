package com.rsicarelli.homehunt_kmm.datasource.cache.mappers

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
        isFavourited = isFavourited,
        isActive = isActive
    )
}


internal fun List<_Property>.toPropertyList(): List<Property> {
    return this.map { it.toProperty() }
}