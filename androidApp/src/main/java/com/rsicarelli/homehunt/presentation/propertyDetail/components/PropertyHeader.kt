package com.rsicarelli.homehunt.presentation.propertyDetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.util.toCurrency
import com.rsicarelli.homehunt_kmm.domain.model.Property
import com.rsicarelli.homehunt.presentation.components.IconText
import com.rsicarelli.homehunt.ui.theme.*
import utils.Fixtures

@Composable
fun PropertyHeader(
    modifier: Modifier = Modifier,
    property: Property,
    contentColor: Color = contentColorFor(backgroundColor = MaterialTheme.colors.background)
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Size_Regular)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (specs, price, title, location) = createRefs()
            val barrier = createEndBarrier(location, specs)

            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top)
                },
                text = property.title,
                style = MaterialTheme.typography.h6.copy(lineHeight = 24.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            IconText(
                modifier = Modifier.constrainAs(location) {
                    top.linkTo(title.bottom, Size_X_Small)
                },
                text = property.location.name,
                textStyle = MaterialTheme.typography.subtitle2.copy(color = contentColor),
                leadingIcon = R.drawable.ic_round_location,
            )

            Row(modifier = Modifier
                .constrainAs(specs) {
                    top.linkTo(location.bottom, Size_X_Small)
                }) {
                IconText(
                    text = property.dormCount.toString(),
                    textStyle = MaterialTheme.typography.subtitle2.copy(color = contentColor),
                    leadingIcon = R.drawable.ic_round_double_bed
                )
                Spacer(modifier = Modifier.width(Size_Small))
                IconText(
                    text = property.bathCount.toString(),
                    textStyle = MaterialTheme.typography.subtitle2.copy(color = contentColor),
                    leadingIcon = R.drawable.ic_round_shower
                )
                Spacer(modifier = Modifier.width(Size_Small))
                IconText(
                    text = "${property.surface} m²",
                    textStyle = MaterialTheme.typography.subtitle2.copy(color = contentColor),
                    leadingIcon = R.drawable.ic_round_ruler
                )
            }

            Text(
                modifier = Modifier.constrainAs(price) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = (-Size_Regular))
                    start.linkTo(barrier, Size_Regular)
                },
                text = "${property.price.toCurrency()}",
                style = MaterialTheme.typography.h4,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF565A5F)
@Composable
private fun PropertyHeaderPreview() {
    HomeHuntTheme {
        Surface {
            PropertyHeader(property = Fixtures.aProperty)
        }
    }
}