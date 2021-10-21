package com.rsicarelli.homehunt_kmm.domain.model

data class Property(
    val reference: String,
    val price: Double,
    val title: String,
    val location: Location,
    val surface: Int,
    val dormCount: Int?,
    val description: String,
    val bathCount: Int?,
    val avatarUrl: String,
    val tag: String?,
    val propertyUrl: String,
    val videoUrl: String?,
    val fullDescription: String?,
    val locationDescription: String?,
    val characteristics: List<String>,
    val photoGalleryUrls: List<String>,
    val pdfUrl: String?,
    val origin: String,
    val viewedBy: List<String?>,
    val isFavourited: Boolean,
    val isActive: Boolean
) {

    //TODO Refactor, should not be here
    fun viewedByMe(): Boolean {
        return false
    }

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

fun Map<String, Any?>.toProperty() =
    Property(
        reference = asString(Mapper.REFERENCE),
        price = asDouble(Mapper.PRICE),
        title = asString(Mapper.TITLE),
        location = asLocation(Mapper.LOCATION),
        surface = asInt(Mapper.SURFACE),
        dormCount = asNullableInt(Mapper.DORM_COUNT),
        description = asString(Mapper.DESCRIPTION),
        bathCount = asNullableInt(Mapper.BATH_COUNT),
        avatarUrl = asString(Mapper.AVATAR_URL),
        tag = asNullableString(Mapper.TAG, default = Property.Tag.EMPTY.identifier),
        propertyUrl = asString(Mapper.PROPERTY_URL),
        videoUrl = asNullableString(Mapper.VIDEO_URL),
        fullDescription = asNullableString(Mapper.FULL_DESCRIPTION),
        characteristics = asStringList(Mapper.CHARACTERISTICS),
        photoGalleryUrls = asStringList(Mapper.PHOTO_GALLERY_URLS),
        pdfUrl = asNullableString(Mapper.PDF_URL),
        locationDescription = asNullableString(Mapper.LOCATION_DESCRIPTION),
        origin = asString(Mapper.ORIGIN),
        viewedBy = asStringList(Mapper.VIEWED_BY),
        isFavourited = asBoolean(Mapper.IS_FAVOURITED),
        isActive = asBoolean(Mapper.IS_ACTIVE)
    )

object Mapper {
    const val REFERENCE = "reference"
    const val PRICE = "price"
    const val TITLE = "title"
    const val LOCATION = "location"
    const val SURFACE = "surface"
    const val DORM_COUNT = "dormCount"
    const val DESCRIPTION = "description"
    const val BATH_COUNT = "bathCount"
    const val AVATAR_URL = "avatarUrl"
    const val TAG = "tag"
    const val PROPERTY_URL = "propertyUrl"
    const val VIDEO_URL = "videoUrl"
    const val FULL_DESCRIPTION = "fullDescription"
    const val CHARACTERISTICS = "characteristics"
    const val PHOTO_GALLERY_URLS = "photoGalleryUrls"
    const val PDF_URL = "pdfUrl"
    const val LOCATION_DESCRIPTION = "locationDescription"
    const val ORIGIN = "origin"
    const val VIEWED_BY = "viewedBy"
    const val IS_FAVOURITED = "isFavourited"
    const val IS_ACTIVE = "isActive"
    const val LOCATION_LAT = "lat"
    const val LOCATION_LNG = "lng"
    const val LOCATION_NAME = "name"
    const val LOCATION_APPROXIMATED = "approximated"
    const val LOCATION_UNKNOWN = "unknown"
}

private fun Map<String, Any?>.asLocation(token: String): Location {
    val locationMap = this[token] as HashMap<String, Any?>
    return Location(
        name = locationMap.asString(Mapper.LOCATION_NAME),
        lat = locationMap.asDouble(Mapper.LOCATION_LAT),
        lng = locationMap.asDouble(Mapper.LOCATION_LNG),
        isApproximated = locationMap.asBoolean(Mapper.LOCATION_APPROXIMATED),
        isUnknown = locationMap.asBoolean(Mapper.LOCATION_UNKNOWN),
    )
}

private fun Map<String, Any?>.asString(token: String) = this[token] as String
private fun Map<String, Any?>.asNullableString(token: String, default: String? = null) =
    (this[token] as String?) ?: default

private fun Map<String, Any?>.asDouble(token: String) = this[token] as Double
private fun Map<String, Any?>.asNullableInt(token: String, default: Int? = null) =
    (this[token] as Long?)?.toInt() ?: default

private fun Map<String, Any?>.asInt(token: String) = (this[token] as Long).toInt()
private fun Map<String, Any?>.asBoolean(token: String) = this[token] as Boolean

private fun Map<String, Any?>.asStringList(token: String) = this[token] as List<String>