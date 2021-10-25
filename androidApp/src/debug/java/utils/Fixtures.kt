package utils

import com.rsicarelli.homehunt_kmm.domain.model.Location
import com.rsicarelli.homehunt_kmm.domain.model.Property

object Fixtures {

    val aProperty = Property(
        _id = "XXXXXXX",
        price = 1000.0,
        title = "A beautiful apartment",
        location = Location(
            name = "Valencia",
            lat = 0.0,
            lng = 0.0,
            isApproximated = false,
            isUnknown = false
        ),
        surface = 60,
        dormCount = 2,
        bathCount = 2,
        avatarUrl = "https://someimage.com",
        tag = "EMPTY",
        propertyUrl = "https://someurl.com",
        videoUrl = "https://somevideo.com",
        fullDescription = "a full description",
        locationDescription = "a location description",
        characteristics = listOf(
            "AAC",
            "Elevator",
            "Balcony",
            "Gas",
            "Pool",
            "Parking",
            "Receptionist",
            "Garden",
            "Heating",
            "Gym",
            "Hot tub",
            "Furnished"
        ),
        photoGalleryUrls = listOf("https://aimage.com".repeat(40)),
        pdfUrl = "https://apdf.com",
        origin = "",
        isUpVoted = false,
        isDownVoted = false,
        isActive = true,
        isViewed = false
    )

    val aListOfProperty = listOf(
        aProperty,
        aProperty.copy(
            title = "Amazing apartment",
            bathCount = 4,
            dormCount = 2,
            surface = 200,
            price = 2000.0
        ),
        aProperty.copy(
            title = "Beutiful house",
            price = 1030.0
        )
    )
}