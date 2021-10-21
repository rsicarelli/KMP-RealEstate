object Google {
    private const val materialVersion = "1.5.0-alpha04"
    const val material = "com.google.android.material:material:$materialVersion"

    private const val mapsKtsVersion = "3.2.0"

    // KTX for the Maps SDK for Android library
    const val mapsKtx = "com.google.maps.android:maps-ktx:$mapsKtsVersion"
    const val mapsUtilsKtx = "com.google.maps.android:maps-utils-ktx:$mapsKtsVersion"

    // It is recommended to also include the latest Maps SDK and/or Utility Library versions
    // as well to ensure that you have the latest features and bug fixes.
    private const val mapsVersion = "17.0.1"
    const val maps = "com.google.android.gms:play-services-maps:$mapsVersion"
}