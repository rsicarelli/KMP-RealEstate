package com.rsicarelli.homehunt.presentation.map.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ktx.awaitMap
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.domain.model.Property
import com.rsicarelli.homehunt.presentation.propertyDetail.components.rememberMapViewWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("PotentialBehaviorOverride")
@Composable
fun PropertiesMapView(
    modifier: Modifier = Modifier,
    onMarkerClick: (Property) -> Unit,
    onMapClick: () -> Unit,
    locations: List<Property>,
    onClusterClick: (List<Property>) -> Unit,
) {
    val mapView = rememberMapViewWithLifecycle(false)
    var isCameraUpdated by remember { mutableStateOf(false) }
    var clusterManager: ClusterManager<PropertyMapItem>? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    AndroidView({ mapView }, modifier = modifier) { map ->
        CoroutineScope(Dispatchers.Main).launch {
            map.awaitMap().apply {
                if (clusterManager == null) {
                    clusterManager = initMap(context, onMarkerClick, onMapClick, onClusterClick)
                }

                clusterManager?.let { cluster ->
                    locations.map { PropertyMapItem(it) }
                        .run {
                            cluster.clearItems()
                            cluster.addItems(this)
                            cluster.cluster()
                        }

                    if (!isCameraUpdated) {
                        val latLngBuilder = LatLngBounds.Builder()

                        cluster.algorithm.items.forEach { item ->
                            latLngBuilder.include(item.position)
                        }

                        moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBuilder.build(), 100))
                        isCameraUpdated = true
                    }
                }
            }
        }
    }
}

@SuppressLint("PotentialBehaviorOverride")
private fun GoogleMap.initMap(
    context: Context,
    onMarkerClick: (Property) -> Unit,
    onMapClick: () -> Unit,
    onClusterClick: (List<Property>) -> Unit
): ClusterManager<PropertyMapItem> {
    val clusterManager = ClusterManager<PropertyMapItem>(context, this)

    clusterManager.renderer = PropertyClusterRenderer(
        context = context,
        map = this,
        clusterManager = clusterManager
    )
    setOnMarkerClickListener(clusterManager)
    clusterManager.setOnClusterItemClickListener { propertyMapItem ->
        onMarkerClick(propertyMapItem.property)
        false
    }
    clusterManager.setOnClusterClickListener { cluster ->
        onClusterClick(cluster.items.map { it.property })
        false
    }
    setOnMapClickListener { onMapClick() }
    setOnCameraIdleListener(clusterManager)
    uiSettings.isMapToolbarEnabled = false

    return clusterManager
}

private fun Context.getBitmapDescriptor(id: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(this, id)?.let {
        it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        val bm: Bitmap =
            Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        it.draw(canvas)
        return fromBitmap(bm)
    }
}

data class PropertyMapItem(
    val property: Property,
    private val title: String = "",
    private val snippet: String = ""
) : ClusterItem {
    override fun getPosition() = LatLng(property.location.lat, property.location.lng)
    override fun getTitle() = title
    override fun getSnippet() = snippet
}

private class PropertyClusterRenderer(
    context: Context, map: GoogleMap,
    clusterManager: ClusterManager<PropertyMapItem>?
) : DefaultClusterRenderer<PropertyMapItem>(
    context, map, clusterManager
) {
    val viewedIcon = context.getBitmapDescriptor(R.drawable.ic_round_marker_grey)
    val notViewedIcon = context.getBitmapDescriptor(R.drawable.ic_round_marker_blue)

    override fun onBeforeClusterItemRendered(item: PropertyMapItem, markerOptions: MarkerOptions) {
        markerOptions.icon(if (item.property.viewedByMe()) viewedIcon else notViewedIcon)
    }

    override fun onClusterItemUpdated(item: PropertyMapItem, marker: Marker) {
        marker.setIcon(if (item.property.viewedByMe()) viewedIcon else notViewedIcon)
    }

}
