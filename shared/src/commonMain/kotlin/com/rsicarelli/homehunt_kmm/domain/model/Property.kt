package com.rsicarelli.homehunt_kmm.domain.model

data class Property(
    val _id: String,
    val price: Double,
    val title: String,
    val location: Location,
    val surface: Int,
    val dormCount: Int,
    val bathCount: Int,
    val avatarUrl: String,
    val tag: String,
    val propertyUrl: String,
    val videoUrl: String?,
    val fullDescription: String,
    val locationDescription: String?,
    val characteristics: List<String>,
    val photoGalleryUrls: List<String>,
    val pdfUrl: String?,
    val origin: String,
    val isViewed: Boolean,
    val isUpVoted: Boolean,
    val isDownVoted: Boolean,
    val isActive: Boolean
) {

    sealed class Tag(val identifier: String) {
        object EMPTY : Tag("")
        object NEW : Tag("NEW")
        object RESERVED : Tag("RESERVED")
        object RENTED : Tag("RENTED")
    }
}

data class Location(
    val lat: Double,
    val lng: Double,
    val name: String,
    val isApproximated: Boolean,
    val isUnknown: Boolean
)

fun String?.toTag(): Property.Tag = this?.let {
    return@let when (it) {
        Property.Tag.NEW.identifier -> Property.Tag.NEW
        Property.Tag.RESERVED.identifier -> Property.Tag.RESERVED
        Property.Tag.RENTED.identifier -> Property.Tag.RENTED
        else -> Property.Tag.EMPTY
    }
} ?: Property.Tag.EMPTY