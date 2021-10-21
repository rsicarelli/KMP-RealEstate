object Dependencies {
    private const val coroutinesVersion = "1.3.9"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"

    private const val touchImageViewVersion = "3.1.1"
    const val touchImageView = "com.github.MikeOrtiz:TouchImageView:$touchImageViewVersion"

    private const val timberVersion = "5.0.1"
    const val timber = "com.jakewharton.timber:timber:$timberVersion"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val android = "android"
}

object Build {

    private const val androidBuildToolsVersion = "7.1.0-beta01"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.hiltVersion}"

    private const val googleServicesVersion = "4.3.10"
    const val googleServicesPlugin =
        "com.google.gms:google-services:$googleServicesVersion"

    private const val googleSecretsVersion = "2.0.0"
    const val googleSecretesPlugin =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:$googleSecretsVersion"
}

object Kotlin {
    const val version = "1.5.31"
}

object Application {
    const val id = "com.rsicarelli.homehunt"
    const val minSdk = 21
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0"
    const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}