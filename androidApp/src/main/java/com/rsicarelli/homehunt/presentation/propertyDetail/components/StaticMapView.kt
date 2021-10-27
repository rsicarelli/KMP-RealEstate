package com.rsicarelli.homehunt.presentation.propertyDetail.components

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import com.rsicarelli.homehunt.R
import com.rsicarelli.homehunt.core.util.getBitmapDescriptor
import com.rsicarelli.homehunt_kmm.domain.model.Location
import com.rsicarelli.homehunt_kmm.domain.model.Property
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/*
* Adapted from https://medium.com/geekculture/google-maps-in-jetpack-compose-android-ae7b1ad84e9
* */
@SuppressLint("PotentialBehaviorOverride")
@Composable
fun StaticMapView(
    modifier: Modifier = Modifier,
    location: Location,
    isLiteMode: Boolean = false,
    showRadius: Boolean = true,
) {
    val mapView = rememberMapViewWithLifecycle(isLiteMode)
    var mapReady by remember { mutableStateOf(false) }
    val context = LocalContext.current

    AndroidView({ mapView }, modifier = modifier) { map ->
        CoroutineScope(Dispatchers.Main).launch {
            map.awaitMap().apply {
                if (!mapReady) {
                    val propertyLocation = LatLng(location.lat, location.lng)

                    val customInfoWindow = CustomInfoWindowAdapter(context, location)
                    setInfoWindowAdapter(customInfoWindow)

                    setPadding(0, 180, 0, 0)
                    uiSettings.isZoomControlsEnabled = true
                    uiSettings.isMapToolbarEnabled = false
                    uiSettings.setAllGesturesEnabled(false)

                    moveCamera(CameraUpdateFactory.newLatLngZoom(propertyLocation, 13f))

                    val markerOptions = MarkerOptions()
                        .icon(context.getBitmapDescriptor(R.drawable.ic_round_marker_blue))
                        .position(propertyLocation)

                    val marker = addMarker(markerOptions)
                    marker?.showInfoWindow()

                    if (location.isApproximated && showRadius) {
                        addCircle(getCircleOptions(propertyLocation))
                    }
                    mapReady = true
                }
            }
        }
    }
}

private fun getCircleOptions(point: LatLng): CircleOptions {
    return CircleOptions().apply {
        center(point)
        radius(500.0)
        strokeColor(Color.BLACK)
        fillColor(0x30ff0000)
        strokeWidth(2f)
    }
}

internal class CustomInfoWindowAdapter(val context: Context, val location: Location) :
    GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker?): View {

        val infoView =
            (context as Activity).layoutInflater.inflate(R.layout.map_infow_window, null)

        infoView.findViewById<TextView>(R.id.infoAddress).text = location.name

        return infoView
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }
}

@Composable
fun rememberMapViewWithLifecycle(isLiteMode: Boolean): MapView {
    val context = LocalContext.current
    val mapView = remember {
        val options = GoogleMapOptions()
            .liteMode(isLiteMode)

        MapView(context, options).apply {
            id = R.id.map
        }
    }

    // Makes MapView follow the lifecycle of this composable
    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }