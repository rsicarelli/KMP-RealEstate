package utils

import com.rsicarelli.homehunt.domain.model.Location
import com.rsicarelli.homehunt.domain.model.Property

object Fixtures {

    val aProperty = Property(
        reference = "XXXXXXX",
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
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas placerat euismod risus, eu blandit turpis euismod id. Nulla eget enim fermentum, fermentum leo ut, faucibus dui. Aenean fringilla porta nisl. In a volutpat turpis. Mauris aliquam ac diam quis elementum. Cras iaculis ex ante, ac pretium ex eleifend in. Vivamus elit sem, ornare vel fringilla ut, faucibus at erat. Aenean dui arcu, auctor eu malesuada eget, convallis at elit. Pellentesque eu mi neque. Sed faucibus quam id lacus venenatis, vitae porttitor urna laoreet.",
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
        viewedBy = emptyList(),
        isFavourited = false,
        isActive = true
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