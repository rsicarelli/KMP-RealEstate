object Compose {
    private const val activityComposeVersion = "1.3.1"
    const val activity = "androidx.activity:activity-compose:$activityComposeVersion"

    const val composeVersion = "1.0.4"
    const val ui = "androidx.compose.ui:ui:$composeVersion"
    const val material = "androidx.compose.material:material:$composeVersion"
    const val tooling = "androidx.compose.ui:ui-tooling:$composeVersion"

    private const val navigationVersion = "2.4.0-alpha10"
    const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"

    private const val hiltNavigationComposeVersion = "1.0.0-alpha03"
    const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationComposeVersion"

    private const val constraintLayoutVersion = "1.0.0-beta02"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout-compose:$constraintLayoutVersion"

    const val utils = "androidx.compose.ui:ui-util:$composeVersion"

}

object ComposeTest {
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Compose.composeVersion}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Compose.composeVersion}"
}