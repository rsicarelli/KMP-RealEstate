package com.rsicarelli.homehunt.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.ui.theme.HomeHuntTheme
import com.rsicarelli.homehunt.ui.theme.Size_Regular
import com.rsicarelli.homehunt.ui.theme.Size_Small
import com.rsicarelli.homehunt.ui.theme.Orange_300

@Composable
fun ListingTag(
    modifier: Modifier = Modifier,
    isPropertyActive: Boolean,
    propertyTag: Property.Tag,
    color: Color = Orange_300,
    style: TextStyle = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
) {
    modifier.padding(top = Size_Regular)

    if (!isPropertyActive || propertyTag is Property.Tag.RENTED || propertyTag is Property.Tag.RESERVED) {

        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            if (!isPropertyActive) {
                Tag(
                    text = stringResource(id = R.string.inactive),
                    modifier = modifier,
                    style = style,
                    color = color
                )
            }

            if (propertyTag is Property.Tag.RENTED) {
                Spacer(modifier = Modifier.width(Size_Small))
                Tag(
                    text = stringResource(id = R.string.rented),
                    modifier = modifier,
                    style = style,
                    color = color
                )
            }

            if (propertyTag is Property.Tag.RESERVED) {
                Spacer(modifier = Modifier.width(Size_Small))
                Tag(
                    text = stringResource(id = R.string.reserved),
                    modifier = modifier,
                    style = style,
                    color = color
                )
            }
        }
    }

}

@Composable
@Preview
private fun ListingTagPreview() {
    HomeHuntTheme(isPreview = true) {
        ListingTag(
            isPropertyActive = false,
            propertyTag = Property.Tag.RENTED
        )
    }
}

@Composable
@Preview
private fun ListingTagInactivePreview() {
    HomeHuntTheme(isPreview = true) {
        ListingTag(
            isPropertyActive = false,
            propertyTag = Property.Tag.NEW
        )
    }
}

@Composable
@Preview
private fun ListingTagRentedPreview() {
    HomeHuntTheme(isPreview = true) {
        ListingTag(
            isPropertyActive = true,
            propertyTag = Property.Tag.RENTED
        )
    }
}

@Composable
@Preview
private fun ListingTagReservedPreview() {
    HomeHuntTheme(isPreview = true) {
        ListingTag(
            isPropertyActive = true,
            propertyTag = Property.Tag.RESERVED
        )
    }
}